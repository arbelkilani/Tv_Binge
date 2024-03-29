package com.arbelkilani.binge.tv.feature.discover

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.presentation.model.Genre
import com.arbelkilani.binge.tv.common.presentation.model.Provider
import com.arbelkilani.binge.tv.feature.home.presentation.model.Tv

class DiscoverContract {
    interface ViewCapabilities {
        suspend fun showGenres(data: PagingData<Genre>)
        suspend fun showProviders(data: PagingData<Provider>)
        suspend fun shows(data: PagingData<Tv>)
    }

    interface ViewNavigation {

    }
}