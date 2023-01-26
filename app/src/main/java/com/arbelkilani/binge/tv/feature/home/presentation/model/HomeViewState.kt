package com.arbelkilani.binge.tv.feature.home.presentation.model

sealed class HomeViewState {
    object Start : HomeViewState()
    data class Error(val exception: Exception) : HomeViewState()
    object Loaded : HomeViewState()
}