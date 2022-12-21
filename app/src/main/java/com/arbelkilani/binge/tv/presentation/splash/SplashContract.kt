package com.arbelkilani.binge.tv.presentation.splash

import androidx.fragment.app.Fragment

interface SplashContract {
    interface ViewCapabilities

    interface ViewNavigation {
        fun navigateToWalkthrough(splashFragment: Fragment)
        fun navigateToDashboard(fragment: Fragment)
    }
}