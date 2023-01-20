package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.model

import com.arbelkilani.binge.tv.common.domain.entities.WatchProviderEntity

sealed class ProvidersSelectionViewState {
    object Start : ProvidersSelectionViewState()
    object Loading : ProvidersSelectionViewState()
    data class Error(val exception: Exception) : ProvidersSelectionViewState()
    object Loaded : ProvidersSelectionViewState()
    data class AddedToFavorite(val provider: WatchProviderEntity) : ProvidersSelectionViewState()
    data class RemovedFromFavorite(val provider: WatchProviderEntity) :
        ProvidersSelectionViewState()
}