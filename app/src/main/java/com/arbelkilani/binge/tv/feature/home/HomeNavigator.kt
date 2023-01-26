package com.arbelkilani.binge.tv.feature.home

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arbelkilani.binge.tv.feature.home.presentation.HomeFragmentDirections
import com.arbelkilani.binge.tv.feature.home.presentation.model.Tv

class HomeNavigator : HomeContract.ViewNavigation {

    override fun navigateToTvDetails(fragment: Fragment, tv: Tv) {
        fragment.findNavController().navigate(
            HomeFragmentDirections.actionNavigationHomeToTvDetailsFragment(tv)
        )
    }
}