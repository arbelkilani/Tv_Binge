package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.listener

import com.arbelkilani.binge.tv.common.domain.entity.WatchProviderEntity

interface ProviderSelectionListener {
    fun removeFromFavorite(position: Int, provider: WatchProviderEntity)
    fun addToFavorite(position: Int, provider: WatchProviderEntity)
    fun showName(provider: WatchProviderEntity)
}