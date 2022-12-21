package com.arbelkilani.binge.tv.presentation.walkthrough

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.arbelkilani.binge.tv.common.base.StateViewModel
import com.arbelkilani.binge.tv.presentation.walkthrough.model.WalkThroughNavEvent
import com.arbelkilani.binge.tv.presentation.walkthrough.model.WalkthroughViewState

interface WalkthroughContract {

    interface ViewCapabilities

    interface ViewModel : StateViewModel<WalkthroughViewState> {

        val navEvent: LiveData<WalkThroughNavEvent>
    }

    interface ViewNavigation {
        fun navigate(navEvent: WalkThroughNavEvent)
        fun navigateToOnBoarding(fragment: Fragment)
    }

    interface ViewTag
}