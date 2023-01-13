package com.arbelkilani.binge.tv.feature.details.presentation.model

import com.arbelkilani.binge.tv.feature.details.domain.entities.TvDetailsEntity

sealed class TvDetailsViewState {
    object Start : TvDetailsViewState()
    object Loading : TvDetailsViewState()
    data class Error(val exception: Exception) : TvDetailsViewState()
    data class Data(val data: TvDetailsEntity) : TvDetailsViewState()
}