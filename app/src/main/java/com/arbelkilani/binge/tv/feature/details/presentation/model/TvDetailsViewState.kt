package com.arbelkilani.binge.tv.feature.details.presentation.model

sealed class TvDetailsViewState {
    object Start : TvDetailsViewState()
    object Loading : TvDetailsViewState()
    data class Error(val exception: Exception) : TvDetailsViewState()
    //data class Data(val ) : TvDetailsViewState()
}