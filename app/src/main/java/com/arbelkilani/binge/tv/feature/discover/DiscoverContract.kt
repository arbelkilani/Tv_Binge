package com.arbelkilani.binge.tv.feature.discover

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState

class DiscoverContract {

    interface ViewCapabilities {
        suspend fun showTrending(state: DiscoverViewState.Trending?)
        suspend fun showStartingThisMonth(state: DiscoverViewState.StartingThisMonth?)
        suspend fun showBasedOnProviders(state: DiscoverViewState.FromProviders?)
        fun showDiscover(data: PagingData<TvEntity>)

        fun showAiringToday(data: PagingData<TvEntity>)
        fun showProviders(data: List<WatchProviderEntity>)
        fun showError(exception: Exception)
    }

    interface ViewNavigation {

    }
}