package com.arbelkilani.binge.tv.presentation.splash.presentation

import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.domain.usecase.GetIsFirstRunUseCase
import com.arbelkilani.binge.tv.presentation.splash.model.SplashViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isFirstRunUseCase: GetIsFirstRunUseCase
) : BaseStateViewModel<SplashViewState>(initialState = SplashViewState.Start) {

    fun getFirstRunState() {
        viewModelScope.launch {
            delay(1000)
            isFirstRunUseCase.execute().collectLatest { isFirstRun ->
                updateState { if (isFirstRun) SplashViewState.IsFirstRun else SplashViewState.IsFirstRun }
            }
        }
    }
}