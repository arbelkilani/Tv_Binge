package com.arbelkilani.binge.tv.feature.details.domain.mapper

import com.arbelkilani.binge.tv.common.data.enum.ImageSize
import com.arbelkilani.binge.tv.common.domain.usecase.GetImageUseCase
import com.arbelkilani.binge.tv.common.presentation.model.Person
import com.arbelkilani.binge.tv.feature.details.domain.entities.CastEntity
import javax.inject.Inject

class CastEntityMapper @Inject constructor() {

    @Inject
    lateinit var getImageUseCase: GetImageUseCase

    suspend fun map(entity: CastEntity) = Person(
        id = entity.id,
        name = entity.name,
        image = getImageUseCase.invoke(endpoint = entity.posterPath, size = ImageSize.PROFILE_W185)
    )
}