package com.arbelkilani.binge.tv.feature.discover

import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv

class DiscoverContract {

    interface ViewCapabilities {
        fun showTrending(data: PagingData<Tv>)
        fun showStartingThisMonth(data: PagingData<Tv>)
        fun getMoreStartingThisMonth()
        fun showBasedOnProviders(data: PagingData<Tv>)
        fun showFree(data: PagingData<Tv>)
        fun showBasedOnGenres(data: PagingData<Tv>)
        fun showUpcoming(data: PagingData<Tv>)
        fun showProviders(providers: List<WatchProviderEntity>?)
        fun showGenres(genres: List<GenreEntity>?)
        fun showError(exception: Exception)
    }

    interface ViewNavigation {
        fun navigateToTvDetails(fragment: Fragment, tv: Tv)
    }
}