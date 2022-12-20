package com.arbelkilani.binge.tv.presentation.ui.onboarding.listener

import com.arbelkilani.binge.tv.domain.entities.WatchProviderEntity

interface WatchProviderListener {
    fun onWatchProviderClicked(provider: WatchProviderEntity, isSelected: Boolean)
}