package com.arbelkilani.binge.tv.feature.discover

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity

class DiscoverContract {

    interface ViewCapabilities {
        fun showTrending(data: List<TvEntity>)
        fun showAiringToday(data: PagingData<TvEntity>)
        fun showError(exception: Exception)
    }

    interface ViewNavigation {

    }
}