package com.arbelkilani.binge.tv.feature.discover.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetAiringTodayUseCase
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetTrendingUseCase
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
    private val getAiringTodayUseCase: GetAiringTodayUseCase
) : BaseStateViewModel<DiscoverViewState>(initialState = DiscoverViewState.Start) {

    fun init() {
        updateState { DiscoverViewState.Loading }
        viewModelScope.launch(Dispatchers.IO) {
            getTrending()
            getAiringToday()
        }
    }

    private suspend fun getTrending() {
        try {
            getTrendingUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .collectLatest { trendingList ->
                    updateState {
                        DiscoverViewState.Loaded(trending = trendingList, PagingData.empty())
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
                .collectLatest { airingTodayPagingData ->
                    updateDataState { state -> state.copy(airingToday = airingTodayPagingData) }
                }
        } catch (exception: Exception) {
            updateState {
                DiscoverViewState.Error(exception)
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