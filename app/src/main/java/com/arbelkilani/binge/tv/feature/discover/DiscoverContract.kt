package com.arbelkilani.binge.tv.feature.discover

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity

class DiscoverContract {

    interface ViewCapabilities {
        suspend fun showTrending(data: List<TvEntity>)
        suspend fun showStartingThisMonth(data: PagingData<TvEntity>)
        fun showBasedOnProviders(data: PagingData<TvEntity>)
        fun showDiscover(data: PagingData<TvEntity>)

        fun showAiringToday(data: PagingData<TvEntity>)
        fun showProviders(data: List<WatchProviderEntity>)
        fun showError(exception: Exception)
    }

    interface ViewNavigation {

    }
}