package com.arbelkilani.binge.tv.feature.onboarding

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.feature.onboarding.presentation.OnBoardingFragmentDirections
import javax.inject.Inject

class OnBoardingNavigator @Inject constructor() : OnBoardingContract.ViewNavigation {

    @Inject
    lateinit var resources: Resources

    override fun navigateToHome(fragment: Fragment) {
        fragment.activity?.let {
            it.findNavController(R.id.nav_host_fragment_activity_main)
                .setGraph(R.navigation.dashboard_navigation)
        }
    }

}