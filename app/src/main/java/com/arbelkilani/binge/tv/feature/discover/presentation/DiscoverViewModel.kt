package com.arbelkilani.binge.tv.feature.discover.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetAiringTodayUseCase
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetDiscoverUseCase
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetTrendingUseCase
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getTrendingUseCase: GetTrendingUseCase,
    private val getAiringTodayUseCase: GetAiringTodayUseCase,
    private val getDiscoverUseCase: GetDiscoverUseCase
) : BaseStateViewModel<DiscoverViewState>(initialState = DiscoverViewState.Start) {

    fun init() {
        updateState { DiscoverViewState.Loading }
        viewModelScope.launch(Dispatchers.IO) {
            getTrending()
            awaitAll(async { getAiringToday() }, async { discover() })
        }
    }

    private suspend fun getTrending() {
        try {
            getTrendingUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .collectLatest { trendingList ->
                    updateState {
                        DiscoverViewState.Loaded(
                            trending = trendingList,
                            PagingData.empty(),
                            PagingData.empty()
                        )
                    }
                }
        } catch (exception: Exception) {
            updateState {
                DiscoverViewState.Error(exception)
            }
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