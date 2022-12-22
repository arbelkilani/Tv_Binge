package com.arbelkilani.binge.tv.feature.onboarding

import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity

class OnBoardingContract {
    
    interface WatchProviderSelectionViewCapabilities {
        fun populateWatchProviders(list: List<WatchProviderEntity>)
    }

    interface ViewNavigation

    interface ViewTag
}