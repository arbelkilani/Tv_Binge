package com.arbelkilani.binge.tv.feature.walkthrough

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arbelkilani.binge.tv.feature.walkthrough.presentation.WalkthroughFragmentDirections
import javax.inject.Inject

class WalkthroughNavigator @Inject constructor() : WalkthroughContract.ViewNavigation {

    @Inject
    lateinit var resources: Resources

    override fun navigateToOnBoarding(fragment: Fragment) {
        fragment.findNavController().navigate(
            WalkthroughFragmentDirections.actionFragmentWalkthroughToFragmentOnBoarding()
        )
    }
}