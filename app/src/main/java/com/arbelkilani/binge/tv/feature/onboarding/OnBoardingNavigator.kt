package com.arbelkilani.binge.tv.feature.onboarding

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.arbelkilani.binge.tv.R
import javax.inject.Inject

class OnBoardingNavigator @Inject constructor() : OnBoardingContract.ViewNavigation {

    @Inject
    lateinit var resources: Resources

    override fun navigateToHome(fragment: Fragment) {
        fragment.activity?.findNavController(R.id.nav_host_fragment_activity_main)?.setGraph(R.navigation.dashboard_navigation)
    }

}