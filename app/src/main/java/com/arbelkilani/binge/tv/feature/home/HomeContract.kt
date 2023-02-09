package com.arbelkilani.binge.tv.feature.home

import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.presentation.model.Person
import com.arbelkilani.binge.tv.feature.home.presentation.model.Tv

class HomeContract {

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