package com.arbelkilani.binge.tv.data.mapper

import com.arbelkilani.binge.tv.data.entities.Provider
import com.arbelkilani.binge.tv.domain.entities.WatchProviderEntity
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