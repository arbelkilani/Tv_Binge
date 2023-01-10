package com.arbelkilani.binge.tv.feature.discover.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetBasedOnProvidersUseCase
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetFreeUseCase
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetStartingThisMonthUseCase
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetTrendingUseCase
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import com.arbelkilani.binge.tv.feature.onboarding.domain.usecase.GetGenresUseCase
import com.arbelkilani.binge.tv.feature.onboarding.domain.usecase.GetProvidersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getTrendingUseCase: GetTrendingUseCase,
    private val getStartingThisMonthUseCase: GetStartingThisMonthUseCase,
    private val getProvidersUseCase: GetProvidersUseCase,
    private val getBasedOnProvidersUseCase: GetBasedOnProvidersUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getFreeUseCase: GetFreeUseCase
) : BaseStateViewModel<DiscoverViewState>(initialState = DiscoverViewState.Start) {

    suspend fun init() {
        updateState { DiscoverViewState.Loading }
        delay(1000)
        getTrending()
        getProviders()
        getStartingThisMonth()
        getBasedOnProviders()
        getGenres()
        getFree()
    }

    private suspend fun getTrending() {
        viewModelScope.launch {
            getTrendingUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
                .collectLatest { data ->
                    updateState { DiscoverViewState.Loaded(trending = data) }
                }
        }
    }

    private suspend fun getStartingThisMonth() {
        viewModelScope.launch {
            getStartingThisMonthUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
                .collectLatest { data ->
                    updateDataState { state -> state.copy(startingThisMonth = data) }
                }
        }
    }

    private suspend fun getProviders() {
        viewModelScope.launch {
            getProvidersUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .collectLatest { list ->
                    updateDataState { state -> state.copy(providers = list) }
                }
        }
    }

    private suspend fun getBasedOnProviders() {
        viewModelScope.launch {
            getBasedOnProvidersUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
                .collectLatest { data ->
                    updateDataState { state -> state.copy(basedOnProvider = data) }
                }
        }
    }

    private suspend fun getGenres() {
        viewModelScope.launch {
            getGenresUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .collectLatest { data ->
                    updateDataState { state ->
                        state.copy(genres = data)
                    }
                }
        }
    }

    private suspend fun getFree() {
        viewModelScope.launch {
            getFreeUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
                .collectLatest { data ->
                    updateDataState { state -> state.copy(free = data) }
                }
        }
    }

    private fun updateDataState(handler: (DiscoverViewState.Loaded) -> (DiscoverViewState)) {
        updateState { state ->
            if (state is DiscoverViewState.Loaded) {
                handler.invoke(state)
            } else state
        }
    }
}