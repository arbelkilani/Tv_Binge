package com.arbelkilani.binge.tv.presentation.viewmodel.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.domain.entities.WatchProviderEntity
import com.arbelkilani.binge.tv.domain.usecase.GetWatchProvidersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val getWatchProvidersUseCase: GetWatchProvidersUseCase
) : ViewModel() {

    private val _providers = MutableStateFlow<List<WatchProviderEntity>>(emptyList())
    val provider: StateFlow<List<WatchProviderEntity>> = _providers

    init {
        viewModelScope.launch {
            getWatchProvidersUseCase.execute()
                .collectLatest {
                    _providers.value = it
                }
        }
    }

    companion object {
        private val TAG = OnBoardingViewModel::class.java.simpleName
    }
}