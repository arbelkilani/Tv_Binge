package com.arbelkilani.binge.tv.feature.onboarding

import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity

class OnBoardingContract {

    interface ProviderSelectionViewCapabilities {
        fun populate(list: List<WatchProviderEntity>)
    }

    interface GenreSelectionViewCapabilities {
        fun populate(list: List<GenreEntity>)
    }

    interface ViewNavigation

    interface ViewTag
}