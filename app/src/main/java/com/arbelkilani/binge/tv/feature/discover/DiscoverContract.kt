package com.arbelkilani.binge.tv.feature.discover

import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.feature.discover.presentation.entities.Person
import com.arbelkilani.binge.tv.feature.discover.presentation.entities.Tv

class DiscoverContract {

    interface ViewCapabilities {
        suspend fun showTrendingShows(data: PagingData<Tv>)
        suspend fun showUpcomingShows(data: PagingData<Tv>)
        suspend fun showTalkShows(data: PagingData<Tv>)
        suspend fun showDocumentaries(data: PagingData<Tv>)
        suspend fun showTrendingPersons(data: PagingData<Person>)
        fun showError(exception: Exception)
    }

    interface ViewNavigation {
        fun navigateToTvDetails(fragment: Fragment, tv: Tv)
    }
}