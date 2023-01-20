package com.arbelkilani.binge.tv.feature.discover.domain.mapper

import com.arbelkilani.binge.tv.common.data.enum.ImageSize
import com.arbelkilani.binge.tv.common.domain.usecase.GetImageUseCase
import com.arbelkilani.binge.tv.feature.discover.domain.entities.PersonEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.entities.Person
import javax.inject.Inject

class PersonEntityMapper @Inject constructor() {

    @Inject
    lateinit var getImageUseCase: GetImageUseCase

    suspend fun map(entity: PersonEntity) = Person(
        id = entity.id,
        name = entity.name,
        image = getImageUseCase.invoke(endpoint = entity.image, size = ImageSize.PROFILE_W92)
    )
}