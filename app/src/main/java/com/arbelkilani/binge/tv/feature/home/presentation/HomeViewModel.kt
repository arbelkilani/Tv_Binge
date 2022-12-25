package com.arbelkilani.binge.tv.feature.home.presentation

import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.home.domain.usecase.ToggleIsFirstRunStateUseCase
import com.arbelkilani.binge.tv.feature.home.presentation.model.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val toggleIsFirstRunStateUseCase: ToggleIsFirstRunStateUseCase
) : BaseStateViewModel<HomeViewState>(
    initialState = HomeViewState.Start
) {

    fun setNoFirstRun() {
        viewModelScope.launch {
            toggleIsFirstRunStateUseCase.invoke()
            updateState { HomeViewState.Loading }
        }
    }
}