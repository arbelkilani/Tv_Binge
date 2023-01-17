package com.arbelkilani.binge.tv.feature.details.domain.mapper

import com.arbelkilani.binge.tv.common.data.enum.ImageSize
import com.arbelkilani.binge.tv.common.domain.usecase.GetLogoUseCase
import com.arbelkilani.binge.tv.feature.details.domain.entities.NetworkEntity
import com.arbelkilani.binge.tv.feature.details.presentation.entities.Network
import javax.inject.Inject

class NetworkEntityMapper @Inject constructor() {

    @Inject
    lateinit var getLogoUseCase: GetLogoUseCase

    suspend fun map(entity: NetworkEntity) = Network(
        id = entity.id,
        name = entity.name,
        logo = entity.logo?.let { getLogoUseCase.invoke(ImageSize.LOGO_W185) + it } ?: run { "" }
    )
}