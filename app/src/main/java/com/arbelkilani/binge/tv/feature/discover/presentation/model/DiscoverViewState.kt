package com.arbelkilani.binge.tv.feature.discover.presentation.model

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity

sealed class DiscoverViewState {
    object Start : DiscoverViewState()
    object Loading : DiscoverViewState()
    data class Error(val exception: Exception) : DiscoverViewState()
    data class Loaded(
        val trending: List<TvEntity>,
        val airingToday: PagingData<TvEntity>,
        val discover: PagingData<TvEntity>
    ) : DiscoverViewState()
}