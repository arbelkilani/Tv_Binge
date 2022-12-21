package com.arbelkilani.binge.tv.presentation.splash.model

sealed class SplashNavEvent {
    object NavigateToWalkthrough: SplashNavEvent()
    object NavigateToDashboard: SplashNavEvent()
}