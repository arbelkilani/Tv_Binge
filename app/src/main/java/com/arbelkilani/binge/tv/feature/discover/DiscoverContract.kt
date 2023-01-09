package com.arbelkilani.binge.tv.feature.discover

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity

class DiscoverContract {

    interface ViewCapabilities {
        suspend fun showTrending(data: PagingData<TvEntity>)
        suspend fun showStartingThisMonth(data: PagingData<TvEntity>)
        suspend fun showBasedOnProviders(data: PagingData<TvEntity>)
        suspend fun showProviders(providers: List<WatchProviderEntity>?)
        fun showError(exception: Exception)
    }

    interface ViewNavigation {

    }
}