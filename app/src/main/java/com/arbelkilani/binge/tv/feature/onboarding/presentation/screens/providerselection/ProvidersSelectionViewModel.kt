package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection

import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.common.domain.entity.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.onboarding.domain.usecase.GetProvidersUseCase
import com.arbelkilani.binge.tv.feature.onboarding.domain.usecase.UpdateProviderUseCase
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.model.ProvidersSelectionViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProvidersSelectionViewModel @Inject constructor(
    private val getProvidersUseCase: GetProvidersUseCase,
    private val updateProviderUseCase: UpdateProviderUseCase
) : BaseStateViewModel<ProvidersSelectionViewState>(
    initialState = ProvidersSelectionViewState.Start
) {

    private val _selectedList = MutableStateFlow(mutableListOf<WatchProviderEntity>())
    val selectedList: StateFlow<MutableList<WatchProviderEntity>> = _selectedList

    private val _unSelectedList = MutableStateFlow(mutableListOf<WatchProviderEntity>())
    val unSelectedList: StateFlow<MutableList<WatchProviderEntity>> = _unSelectedList

    fun load() {
        updateState { ProvidersSelectionViewState.Loading }
        viewModelScope.launch {
            try {
                getProvidersUseCase.invoke().collectLatest { providers ->
                    _selectedList.value =
                        providers.filter { it.isFavorite }.toMutableList()
                    _unSelectedList.value =
                        providers.filterNot { it.isFavorite }.toMutableList()
                }
                updateState { ProvidersSelectionViewState.Loaded }
            } catch (exception: Exception) {
                exception.printStackTrace()
                updateState { ProvidersSelectionViewState.Error(exception) }
            }
        }
    }

    fun addToFavorite(provider: WatchProviderEntity) {
        viewModelScope.launch {
            updateProviderUseCase.invoke(provider)
        }
        _selectedList.updateAndGet { it.apply { add(0, provider) } }
        _unSelectedList.updateAndGet { it.apply { remove(provider.copy(isFavorite = false)) } }
        updateState { ProvidersSelectionViewState.AddedToFavorite(provider) }
    }

    fun removeFromFavorite(provider: WatchProviderEntity) {
        viewModelScope.launch {
            updateProviderUseCase.invoke(provider)
        }
        _unSelectedList.updateAndGet { it.apply { add(0, provider) } }
        _selectedList.updateAndGet { it.apply { remove(provider.copy(isFavorite = true)) } }
        updateState { ProvidersSelectionViewState.RemovedFromFavorite(provider) }
    }
}