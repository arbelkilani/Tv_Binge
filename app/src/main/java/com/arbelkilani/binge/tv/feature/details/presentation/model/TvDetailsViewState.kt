package com.arbelkilani.binge.tv.feature.details.presentation.model

sealed class TvDetailsViewState {
    object Start : TvDetailsViewState()
    object Loading : TvDetailsViewState()
    data class Error(val throwable: Throwable) : TvDetailsViewState()
    object Loaded : TvDetailsViewState()
}