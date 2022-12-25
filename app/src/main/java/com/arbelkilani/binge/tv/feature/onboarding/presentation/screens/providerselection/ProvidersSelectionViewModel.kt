package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.onboarding.domain.usecase.GetProvidersUseCase
import com.arbelkilani.binge.tv.feature.onboarding.domain.usecase.UpdateProviderUseCase
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.model.ProvidersSelectionViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    private val _selectedList = MutableStateFlow(mutableListOf<WatchProviderEntity>())
    val selectedList: StateFlow<MutableList<WatchProviderEntity>> = _selectedList

    private val _unSelectedList = MutableStateFlow(mutableListOf<WatchProviderEntity>())
    val unSelectedList: StateFlow<MutableList<WatchProviderEntity>> = _unSelectedList

    fun load() {
        viewModelScope.launch {
            getProvidersUseCase.getProviders().collectLatest { providers ->
                _selectedList.value =
                    providers.filter { it.isFavorite }.toMutableList()
                _unSelectedList.value =
                    providers.filterNot { it.isFavorite }.toMutableList()
            }
        }
    }

    fun addToFavorite(provider: WatchProviderEntity) {
        viewModelScope.launch {
            updateProviderUseCase.invoke(provider.copy(isFavorite = true))
        }
        _selectedList.updateAndGet { it.apply { add(provider.copy(isFavorite = true)) } }
        _unSelectedList.updateAndGet { it.apply { remove(provider) } }
    }

    fun removeFromFavorite(provider: WatchProviderEntity) {
        viewModelScope.launch {
            updateProviderUseCase.invoke(provider.copy(isFavorite = false))
        }
        _unSelectedList.updateAndGet { it.apply { add(provider.copy(isFavorite = false)) } }
        _selectedList.updateAndGet { it.apply { remove(provider) } }
    }
}