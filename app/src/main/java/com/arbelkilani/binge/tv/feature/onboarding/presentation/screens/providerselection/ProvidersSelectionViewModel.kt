package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection

import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.onboarding.domain.usecase.GetProvidersUseCase
import com.arbelkilani.binge.tv.feature.onboarding.domain.usecase.UpdateProviderUseCase
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.model.ProvidersSelectionViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProvidersSelectionViewModel @Inject constructor(
    private val getProvidersUseCase: GetProvidersUseCase,
    private val updateProviderUseCase: UpdateProviderUseCase
) :
    BaseStateViewModel<ProvidersSelectionViewState>(
        initialState = ProvidersSelectionViewState.Start
    ) {

    fun load() {
        viewModelScope.launch {
            getProvidersUseCase.getProviders().collectLatest { providers ->
                updateState { ProvidersSelectionViewState.Loaded(providers) }
            }
        }
    }

    fun updateProvider(provider: WatchProviderEntity) {
        viewModelScope.launch {
            updateProviderUseCase.invoke(provider)
        }
    }
}