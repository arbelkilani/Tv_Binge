package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.model

import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity

sealed class ProvidersSelectionViewState {
    object Start : ProvidersSelectionViewState()
    data class Loaded(val list: List<WatchProviderEntity>) : ProvidersSelectionViewState()
    data class SelectedItem(val provider: WatchProviderEntity?) :
        ProvidersSelectionViewState()
}