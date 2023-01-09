package com.arbelkilani.binge.tv.feature.discover

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity

class DiscoverContract {

    interface ViewCapabilities {
        fun showTrending(data: PagingData<TvEntity>)
        fun showStartingThisMonth(data: PagingData<TvEntity>)
        fun getMoreStartingThisMonth()
        fun showBasedOnProviders(data: PagingData<TvEntity>)
        fun showFree(data: PagingData<TvEntity>)
        fun showProviders(providers: List<WatchProviderEntity>?)
        fun showGenres(genres: List<GenreEntity>?)
        fun showError(exception: Exception)
    }

    interface ViewNavigation {

    }
}