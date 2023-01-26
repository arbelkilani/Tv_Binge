package com.arbelkilani.binge.tv.common.domain.mapper

import com.arbelkilani.binge.tv.common.domain.entity.ProviderEntity
import com.arbelkilani.binge.tv.common.presentation.model.Provider
import javax.inject.Inject

class ProviderEntityMapper @Inject constructor() {
    fun map(providerEntity: ProviderEntity) = Provider(
        id = providerEntity.id,
        name = providerEntity.name,
        type = providerEntity.type,
        logo = providerEntity.logo,
        link = providerEntity.link,
        isFavorite = providerEntity.isFavorite
    )
}