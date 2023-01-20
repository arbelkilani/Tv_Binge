package com.arbelkilani.binge.tv.feature.onboarding

import androidx.fragment.app.Fragment
import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.common.domain.entity.WatchProviderEntity

class OnBoardingContract {

    interface ProviderSelectionViewCapabilities {
        fun setFavorites(list: List<WatchProviderEntity>)
        fun setOthers(list: List<WatchProviderEntity>)
    }

    interface GenreSelectionViewCapabilities {
        fun populate(list: List<GenreEntity>)
    }

    interface ViewNavigation {
        fun navigateToHome(fragment: Fragment)
    }
}