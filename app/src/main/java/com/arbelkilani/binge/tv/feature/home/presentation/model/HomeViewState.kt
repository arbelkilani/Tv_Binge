package com.arbelkilani.binge.tv.feature.home.presentation.model

abstract class HomeViewState {
    object Start : HomeViewState()
    object Loading: HomeViewState()
}