package com.arbelkilani.binge.tv.common.data.mapper

import com.arbelkilani.binge.tv.common.data.response.Provider
import com.arbelkilani.binge.tv.common.domain.entity.WatchProviderEntity
import javax.inject.Inject

class WatchProviderMapper @Inject constructor() {

    fun map(provider: Provider, isFavorite: Boolean) = WatchProviderEntity(
        id = provider.providerId,
        name = provider.providerName,
        logo = provider.logoPath,
        priority = provider.displayPriority,
        isFavorite = isFavorite
    )
}