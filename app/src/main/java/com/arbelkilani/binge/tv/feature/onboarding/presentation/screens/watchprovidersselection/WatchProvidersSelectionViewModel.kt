package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.watchprovidersselection

import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.watchprovidersselection.model.WatchProvidersSelectionViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchProvidersSelectionViewModel @Inject constructor() :
    BaseStateViewModel<WatchProvidersSelectionViewState.Start>(
        initialState = WatchProvidersSelectionViewState.Start
    ) {


}