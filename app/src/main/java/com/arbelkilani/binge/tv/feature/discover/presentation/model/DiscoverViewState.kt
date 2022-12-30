package com.arbelkilani.binge.tv.feature.discover.presentation.model

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity

sealed class DiscoverViewState {

    sealed class Data : DiscoverViewState() {
        sealed class TrendingState : Data() {
            data class Success(val trending: List<TvEntity>) : Data()
            data class Fail(val exception: Exception) : Data()
        }

        sealed class AiringTodayState : Data() {
            data class Success(val airingToday: PagingData<TvEntity>) : Data()
            data class Fail(val exception: Exception) : Data()
        }
    }

    object Start : DiscoverViewState()
    object Loading : DiscoverViewState()
}