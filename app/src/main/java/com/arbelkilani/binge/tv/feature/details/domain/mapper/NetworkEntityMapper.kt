package com.arbelkilani.binge.tv.feature.details.domain.mapper

import com.arbelkilani.binge.tv.common.data.enum.ImageSize
import com.arbelkilani.binge.tv.common.domain.usecase.GetImageUseCase
import com.arbelkilani.binge.tv.feature.details.domain.entities.NetworkEntity
import com.arbelkilani.binge.tv.feature.details.presentation.entities.Network
import javax.inject.Inject

class NetworkEntityMapper @Inject constructor() {

    @Inject
    lateinit var getImageUseCase: GetImageUseCase

    suspend fun map(entity: NetworkEntity) = Network(
        id = entity.id,
        name = entity.name,
        logo = getImageUseCase.invoke(endpoint = entity.logo, size = ImageSize.LOGO_W92)
    )
}