package com.arbelkilani.binge.tv.feature.discover.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetAiringTodayUseCase
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetTrendingUseCase
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
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
            try {
                combine(
                    getTrendingUseCase.invoke(),
                    getAiringTodayUseCase.invoke().cachedIn(viewModelScope)
                ) { trending: List<TvEntity>, airingToday: PagingData<TvEntity> ->
                    return@combine Pair(trending, airingToday)
                }.flowOn(Dispatchers.IO)
                    .collect { pair ->
                        updateState { DiscoverViewState.Loaded(pair.first, pair.second) }
                    }
            } catch (exception: Exception) {
                updateState { DiscoverViewState.Error(exception) }
            }
        }
    }

    private suspend fun getTrending() {
        try {
            getTrendingUseCase.invoke()
                .flowOn(Dispatchers.IO)
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
                .flowOn(Dispatchers.IO)
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