package com.arbelkilani.binge.tv.feature.walkthrough.presentation.model

abstract class WalkthroughViewState {
    object Start : WalkthroughViewState()
    data class Error(val exception: Exception) : WalkthroughViewState()
}