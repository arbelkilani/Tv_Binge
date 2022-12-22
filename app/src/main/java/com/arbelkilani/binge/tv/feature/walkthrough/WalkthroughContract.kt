package com.arbelkilani.binge.tv.feature.walkthrough

import androidx.fragment.app.Fragment

interface WalkthroughContract {

    interface ViewCapabilities

    interface ViewNavigation {
        fun navigateToOnBoarding(fragment: Fragment)
    }

    interface ViewTag
}