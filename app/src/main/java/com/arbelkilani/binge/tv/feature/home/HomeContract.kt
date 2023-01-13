package com.arbelkilani.binge.tv.feature.home

import androidx.fragment.app.Fragment
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv

interface HomeContract {

    interface ViewCapabilities {
        fun setNoFirstRun()
    }

    interface ViewNavigation {
        fun navigateToTvDetails(fragment: Fragment, tv: Tv)
    }

    interface ViewTag
}