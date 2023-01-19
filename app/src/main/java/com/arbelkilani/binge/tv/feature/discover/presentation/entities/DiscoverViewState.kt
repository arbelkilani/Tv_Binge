package com.arbelkilani.binge.tv.feature.discover.presentation.entities

sealed class DiscoverViewState {
    object Start : DiscoverViewState()
    data class Error(val exception: Exception) : DiscoverViewState()
    object Loaded : DiscoverViewState()
}