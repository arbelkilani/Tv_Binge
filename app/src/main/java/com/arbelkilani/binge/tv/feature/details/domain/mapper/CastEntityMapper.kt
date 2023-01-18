package com.arbelkilani.binge.tv.feature.details.domain.mapper

import com.arbelkilani.binge.tv.common.data.enum.ImageSize
import com.arbelkilani.binge.tv.common.domain.usecase.GetProfileUseCase
import com.arbelkilani.binge.tv.feature.details.domain.entities.CastEntity
import com.arbelkilani.binge.tv.feature.details.presentation.entities.Cast
import javax.inject.Inject

class CastEntityMapper @Inject constructor() {

    @Inject
    lateinit var getProfileUseCase: GetProfileUseCase

    suspend fun map(entity: CastEntity) = Cast(
        id = entity.id,
        name = entity.name,
        image = entity.posterPath?.let { getProfileUseCase.invoke(ImageSize.PROFILE_W185) + it }
            ?: run { "" }
    )
}