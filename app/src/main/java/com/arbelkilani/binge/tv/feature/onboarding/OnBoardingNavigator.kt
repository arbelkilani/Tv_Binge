package com.arbelkilani.binge.tv.feature.onboarding

import android.content.res.Resources
import javax.inject.Inject

class OnBoardingNavigator @Inject constructor() : OnBoardingContract.ViewNavigation {

    @Inject
    lateinit var resources: Resources

}