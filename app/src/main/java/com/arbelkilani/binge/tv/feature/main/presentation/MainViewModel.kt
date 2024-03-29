package com.arbelkilani.binge.tv.feature.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.feature.main.domain.usecase.GetIsFirstRunUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getIsFirstRunUseCase: GetIsFirstRunUseCase
) : ViewModel() {

    private val _isFirstRun = MutableStateFlow(false)
    val isFirstRun: StateFlow<Boolean> = _isFirstRun

    init {
        viewModelScope.launch {
            getIsFirstRunUseCase.execute().collectLatest { _isFirstRun.value = it }
        }
    }
}