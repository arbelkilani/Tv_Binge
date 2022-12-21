package com.arbelkilani.binge.tv.presentation.walkthrough.model

sealed class WalkThroughNavEvent {
    object NavigateToOnBoarding : WalkThroughNavEvent()
}