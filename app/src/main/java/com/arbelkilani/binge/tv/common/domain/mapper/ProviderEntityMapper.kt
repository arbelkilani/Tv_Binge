package com.arbelkilani.binge.tv.common.domain.mapper

import com.arbelkilani.binge.tv.common.data.enum.ImageSize
import com.arbelkilani.binge.tv.common.domain.entity.ProviderEntity
import com.arbelkilani.binge.tv.common.domain.usecase.GetImageUseCase
import com.arbelkilani.binge.tv.common.presentation.model.Provider
import javax.inject.Inject

class ProviderEntityMapper @Inject constructor() {

    @Inject
    lateinit var getImageUseCase: GetImageUseCase

    suspend fun map(providerEntity: ProviderEntity) = Provider(
        id = providerEntity.id,
        name = providerEntity.name,
        type = providerEntity.type,
        logo = getImageUseCase.invoke(providerEntity.logo, ImageSize.LOGO_W154),
        link = providerEntity.link,
        isFavorite = providerEntity.isFavorite
    )
}