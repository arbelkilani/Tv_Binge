package com.arbelkilani.binge.tv.feature.discover

import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState

class DiscoverContract {

    interface ViewCapabilities {
        suspend fun showTrending(state: DiscoverViewState.Trending?)
        suspend fun showStartingThisMonth(state: DiscoverViewState.StartingThisMonth?)
        suspend fun showBasedOnProviders(state: DiscoverViewState.BasedOnProviders?)
        fun showError(exception: Exception)
    }

    interface ViewNavigation {

    }
}