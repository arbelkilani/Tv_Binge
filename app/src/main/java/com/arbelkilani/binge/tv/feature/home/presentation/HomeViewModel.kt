package com.arbelkilani.binge.tv.feature.home.presentation

import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.home.domain.usecase.ToggleIsFirstRunStateUseCase
import com.arbelkilani.binge.tv.feature.home.presentation.model.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val toggleIsFirstRunStateUseCase: ToggleIsFirstRunStateUseCase
) : BaseStateViewModel<HomeViewState>(
    initialState = HomeViewState.Start
) {


    fun toggleIsFirstRunState() {
        //viewModelScope.launch {
        //    toggleIsFirstRunStateUseCase.invoke()
        //}
        updateState { HomeViewState.Loading }
    }
}