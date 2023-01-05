package com.arbelkilani.binge.tv.feature.discover.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.*
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getTrendingUseCase: GetTrendingUseCase,
    private val getStartingThisMonthUseCase: GetStartingThisMonthUseCase,
    private val getAiringTodayUseCase: GetAiringTodayUseCase,
    private val getDiscoverUseCase: GetDiscoverUseCase,
    private val getFavoriteProvidersUseCase: GetFavoriteProvidersUseCase
) : BaseStateViewModel<DiscoverViewState>(initialState = DiscoverViewState.Start) {

    fun init() {
        updateState { DiscoverViewState.Loading }
        viewModelScope.launch(Dispatchers.IO) {
            getTrending()
            awaitAll(async { getStartingThisMonth() })

            //getFavoriteProviders()
            //awaitAll(async { getAiringToday() }, async { discover() })
        }
    }

    private suspend fun getTrending() {
        try {
            getTrendingUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .collectLatest { trendingList ->
                    updateState {
                        DiscoverViewState.Loaded(
                            trending = trendingList
                        )
                    }
                }
        } catch (exception: Exception) {
            updateState {
                DiscoverViewState.Error(exception)
            }
        }
    }

    private suspend fun getStartingThisMonth() {
        try {
            getStartingThisMonthUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
                .collectLatest { data ->
                    updateDataState { state -> state.copy(startingThisMonth = data) }
                }
        } catch (exception: Exception) {
            updateState { DiscoverViewState.Error(exception) }
        }
    }

    private suspend fun getFavoriteProviders() {
        try {
            getFavoriteProvidersUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .collectLatest { data ->
                    data?.let {
                        updateDataState { state -> state.copy(providers = it) }
                    }
                }

        } catch (exception: Exception) {
            updateState { DiscoverViewState.Error(exception) }
        }
    }

    private suspend fun getAiringToday() {
        try {
            getAiringTodayUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
                .collect { airingTodayPagingData ->
                    updateDataState { state -> state.copy(airingToday = airingTodayPagingData) }
                }
        } catch (exception: Exception) {
            updateState {
                DiscoverViewState.Error(exception)
            }
        }
    }

    private suspend fun discover() {
        try {
            getDiscoverUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
                .collect { data ->
                    updateDataState { state -> state.copy(discover = data) }
                }
        } catch (exception: Exception) {
            updateState { DiscoverViewState.Error(exception = exception) }
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