package com.arbelkilani.binge.tv.feature.discover.presentation.model

sealed class DiscoverViewState {
    object Start : DiscoverViewState()
    data class Error(val exception: Exception) : DiscoverViewState()
    object Loading : DiscoverViewState()
    object Loaded : DiscoverViewState()
}