package com.arbelkilani.binge.tv.feature.discover

import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv

class DiscoverContract {

    interface ViewCapabilities {
        suspend fun showTrending(data: PagingData<Tv>)
        suspend fun showUpcoming(data: PagingData<Tv>)
        suspend fun showTalkShows(data: PagingData<Tv>)
        fun showError(exception: Exception)
    }

    interface ViewNavigation {
        fun navigateToTvDetails(fragment: Fragment, tv: Tv)
    }
}