package com.arbelkilani.binge.tv.feature.discover.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.*
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getTrendingUseCase: GetTrendingUseCase,
    private val getStartingThisMonthUseCase: GetStartingThisMonthUseCase,
    private val getBasedOnProvidersUseCase: GetBasedOnProvidersUseCase
) : BaseStateViewModel<DiscoverViewState>(initialState = DiscoverViewState.Start) {

    suspend fun init() {
        updateState { DiscoverViewState.Loading }
        getTrending()
        getStartingThisMonth()
        getBasedOnProviders()
    }

    private suspend fun getTrending() {
        viewModelScope.launch {
            getTrendingUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
                .collectLatest { trendingList ->
                    updateDataState { state ->
                        state.copy(
                            trending = DiscoverViewState.Trending(
                                list = trendingList
                            )
                        )
                    }
                }
        }
    }

    private suspend fun getStartingThisMonth() {
        viewModelScope.launch {
            getStartingThisMonthUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
                .collectLatest { data ->
                    updateDataState { state ->
                        state.copy(
                            startingThisMonth = DiscoverViewState.StartingThisMonth(
                                data = data
                            )
                        )
                    }
                }
        }
    }

    private suspend fun getBasedOnProviders() {
        viewModelScope.launch {
            getBasedOnProvidersUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
                .collectLatest { data ->
                    updateDataState { state ->
                        state.copy(
                            basedOnProvider = DiscoverViewState.BasedOnProviders(
                                data = data
                            )
                        )
                    }
                }
        }
    }

    private fun updateDataState(handler: (DiscoverViewState.Loaded) -> (DiscoverViewState)) {
        updateState { DiscoverViewState.Loaded() }
        updateState { state ->
            if (state is DiscoverViewState.Loaded) {
                handler.invoke(state)
            } else state
        }
    }
}