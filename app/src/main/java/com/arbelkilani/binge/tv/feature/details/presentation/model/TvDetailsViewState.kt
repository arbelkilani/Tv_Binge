package com.arbelkilani.binge.tv.feature.details.presentation.model

import com.arbelkilani.binge.tv.feature.details.presentation.entities.TvDetails

sealed class TvDetailsViewState {
    object Start : TvDetailsViewState()
    object Loading : TvDetailsViewState()
    data class Error(val throwable: Throwable) : TvDetailsViewState()
    data class Data(val data: TvDetails) : TvDetailsViewState()
}