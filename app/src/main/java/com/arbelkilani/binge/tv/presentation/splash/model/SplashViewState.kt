package com.arbelkilani.binge.tv.presentation.splash.model

sealed class SplashViewState {
    object Start : SplashViewState()
    object IsFirstRun : SplashViewState()
    object IsNotFirstRun : SplashViewState()
}