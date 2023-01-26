package com.arbelkilani.binge.tv.feature.discover

import com.arbelkilani.binge.tv.common.presentation.model.Genre

class DiscoverContract {
    interface ViewCapabilities {
        suspend fun showGenres(data: List<Genre>)
    }

    interface ViewNavigation {

    }
}