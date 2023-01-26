package com.arbelkilani.binge.tv.common.data.mapper

import com.arbelkilani.binge.tv.common.data.response.WatchProviderResponse
import com.arbelkilani.binge.tv.common.domain.entity.ProviderEntity
import javax.inject.Inject

class WatchProviderResponseMapper @Inject constructor() {
    fun map(response: WatchProviderResponse, isFavorite: Boolean) = ProviderEntity(
        id = response.providerId,
        name = response.providerName,
        logo = response.logoPath,
        isFavorite = isFavorite
    )
}
