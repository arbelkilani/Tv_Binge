package com.arbelkilani.binge.tv.presentation.splash

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arbelkilani.binge.tv.presentation.splash.presentation.SplashFragmentDirections

class SplashNavigator : SplashContract.ViewNavigation {

    // TODO: Check whether we can inject fragment directly

    override fun navigateToWalkthrough(fragment: Fragment) {
        fragment.findNavController()
            .navigate(SplashFragmentDirections.actionNavigationSplashToNavigationWalkthrough())
    }

    override fun navigateToDashboard(fragment: Fragment) {
        fragment.findNavController()
            .navigate(SplashFragmentDirections.actionNavigationSplashToNavigationDashboard())
    }
}