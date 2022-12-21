package com.arbelkilani.binge.tv.presentation.walkthrough

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arbelkilani.binge.tv.presentation.walkthrough.model.WalkThroughNavEvent
import com.arbelkilani.binge.tv.presentation.walkthrough.presentation.WalkthroughFragmentDirections
import javax.inject.Inject

class WalkthroughNavigator @Inject constructor() : WalkthroughContract.ViewNavigation {

    @Inject
    lateinit var resources: Resources

    override fun navigate(navEvent: WalkThroughNavEvent) {}

    override fun navigateToOnBoarding(fragment: Fragment) {
        fragment.findNavController().navigate(
            WalkthroughFragmentDirections.actionFragmentWalkthroughToFragmentOnBoarding()
        )
    }
}