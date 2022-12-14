package com.arbelkilani.binge.tv.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.domain.usecase.GetIsFirstRunUseCase
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

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    init {
        viewModelScope.launch {
            getIsFirstRunUseCase.execute().collectLatest {
                _isFirstRun.value = it
            }
        }
    }
}