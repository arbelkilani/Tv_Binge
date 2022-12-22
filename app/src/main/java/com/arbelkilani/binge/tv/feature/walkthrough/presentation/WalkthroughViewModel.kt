package com.arbelkilani.binge.tv.feature.walkthrough.presentation

import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.walkthrough.domain.usecase.SaveResourcesUseCase
import com.arbelkilani.binge.tv.feature.walkthrough.presentation.model.WalkthroughViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalkthroughViewModel @Inject constructor(
    private val saveResourcesUseCase: SaveResourcesUseCase
) : BaseStateViewModel<WalkthroughViewState>(
    initialState = WalkthroughViewState.Start
) {

    fun saveResources() {
        viewModelScope.launch {
            saveResourcesUseCase.execute()
        }
    }
}