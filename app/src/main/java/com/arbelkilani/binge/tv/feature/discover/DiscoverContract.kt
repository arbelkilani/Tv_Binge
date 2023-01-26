package com.arbelkilani.binge.tv.feature.discover

import com.arbelkilani.binge.tv.common.presentation.model.Genre
import com.arbelkilani.binge.tv.common.presentation.model.Provider

class DiscoverContract {
    interface ViewCapabilities {
        suspend fun showGenres(data: List<Genre>)
        suspend fun showProviders(data: List<Provider>)
    }

    interface ViewNavigation {

    }
}