package com.arbelkilani.binge.tv.feature.discover.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetAiringTodayUseCase
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetTrendingUseCase
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getTrendingUseCase: GetTrendingUseCase,
    private val getAiringTodayUseCase: GetAiringTodayUseCase
) : BaseStateViewModel<DiscoverViewState>(initialState = DiscoverViewState.Start) {

    fun init() {
        updateState { DiscoverViewState.Loading }
        viewModelScope.launch {
            awaitAll(
                async { getTrending() },
                async { getAiringToday() }
            )
        }
    }

    private suspend fun getTrending() {
        try {
            getTrendingUseCase.invoke()
                .collectLatest { trendingList ->
                    updateState {
                        DiscoverViewState.Data.TrendingState.Success(trendingList)
                    }
                }
        } catch (exception: Exception) {
            updateState {
                DiscoverViewState.Data.TrendingState.Fail(exception)
            }
        }
    }

    private suspend fun getAiringToday() {
        try {
            getAiringTodayUseCase.invoke()
                .cachedIn(viewModelScope)
                .collectLatest { airingTodayPagingData ->
                    updateState {
                        DiscoverViewState.Data.AiringTodayState.Success(airingTodayPagingData)
                    }
                }
        } catch (exception: Exception) {
            updateState {
                DiscoverViewState.Data.AiringTodayState.Fail(exception)
            }
        }
    }
}