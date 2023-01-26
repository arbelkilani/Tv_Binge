package com.arbelkilani.binge.tv.feature.search

import com.arbelkilani.binge.tv.common.presentation.model.Genre

class SearchContract {
    interface ViewCapabilities {
        suspend fun showGenres(data: List<Genre>)
    }

    interface ViewNavigation {

    }
}