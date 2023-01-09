package com.arbelkilani.binge.tv.feature.discover.presentation.model

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity

sealed class DiscoverViewState {
    object Start : DiscoverViewState()
    object Loading : DiscoverViewState()
    data class Error(val exception: Exception) : DiscoverViewState()
    data class Loaded(
        val trending: Trending? = null,
        val startingThisMonth: StartingThisMonth? = null,
        val basedOnProvider: BasedOnProviders? = null,
        val airingToday: PagingData<TvEntity> = PagingData.empty(),
        val discover: PagingData<TvEntity> = PagingData.empty(),
        val providers: List<WatchProviderEntity> = emptyList()
    ) : DiscoverViewState()

    data class Trending(
        val list: PagingData<TvEntity> = PagingData.empty(),
        val exception: Exception? = null
    )

    data class StartingThisMonth(
        val data: PagingData<TvEntity> = PagingData.empty(),
        val exception: Exception? = null
    )

    data class BasedOnProviders(
        val data: PagingData<TvEntity> = PagingData.empty(),
        val exception: Exception? = null
    )
}