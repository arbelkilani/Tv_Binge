package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.listener

import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity

interface ProviderSelectionListener {
    fun onWatchProviderClicked(provider: WatchProviderEntity)
}