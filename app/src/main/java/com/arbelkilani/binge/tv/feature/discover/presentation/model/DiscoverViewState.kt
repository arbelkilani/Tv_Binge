package com.arbelkilani.binge.tv.feature.discover.presentation.model

import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity

abstract class DiscoverViewState {
    object Start : DiscoverViewState()
    object Loading : DiscoverViewState()
    data class HttpException(val exception: Exception) : DiscoverViewState()
    data class IOException(val exception: Exception) : DiscoverViewState()
    data class UnknownException(val exception: Exception) : DiscoverViewState()
    data class TrendingLoaded(val data: List<TvEntity>) : DiscoverViewState()
}