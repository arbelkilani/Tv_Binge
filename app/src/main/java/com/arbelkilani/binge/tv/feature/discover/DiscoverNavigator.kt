package com.arbelkilani.binge.tv.feature.discover

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arbelkilani.binge.tv.feature.discover.presentation.DiscoverFragmentDirections
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv

class DiscoverNavigator : DiscoverContract.ViewNavigation {

    override fun navigateToTvDetails(fragment: Fragment, tv: Tv) {
        fragment.findNavController().navigate(
            DiscoverFragmentDirections.actionNavigationDiscoverToTvDetailsFragment(tv)
        )
    }
}