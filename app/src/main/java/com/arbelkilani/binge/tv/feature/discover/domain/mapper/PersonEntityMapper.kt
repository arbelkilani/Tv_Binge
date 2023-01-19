package com.arbelkilani.binge.tv.feature.discover.domain.mapper

import com.arbelkilani.binge.tv.common.data.enum.ImageSize
import com.arbelkilani.binge.tv.common.domain.usecase.GetProfileUseCase
import com.arbelkilani.binge.tv.feature.discover.domain.entities.PersonEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.entities.Person
import javax.inject.Inject

class PersonEntityMapper @Inject constructor() {

    @Inject
    lateinit var profileUseCase: GetProfileUseCase

    suspend fun map(entity: PersonEntity) = Person(
        id = entity.id,
        name = entity.name,
        image = entity.image?.let { profileUseCase.invoke(ImageSize.PROFILE_W185) + it }
            ?: run { "" }
    )
}