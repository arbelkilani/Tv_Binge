package com.arbelkilani.binge.tv.presentation.walkthrough.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.presentation.walkthrough.WalkthroughContract
import com.arbelkilani.binge.tv.presentation.walkthrough.model.WalkThroughNavEvent
import com.arbelkilani.binge.tv.presentation.walkthrough.model.WalkthroughViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalkthroughViewModel @Inject constructor() : WalkthroughContract.ViewModel,
    BaseStateViewModel<WalkthroughViewState>(
        initialState = WalkthroughViewState.Start
    ) {

    override val navEvent: LiveData<WalkThroughNavEvent>
        get() = MutableLiveData()

}