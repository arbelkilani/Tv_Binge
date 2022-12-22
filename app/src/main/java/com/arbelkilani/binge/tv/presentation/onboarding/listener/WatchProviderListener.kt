package com.arbelkilani.binge.tv.presentation.onboarding.listener

import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity

interface WatchProviderListener {
    fun onWatchProviderClicked(provider: WatchProviderEntity, isSelected: Boolean)
}