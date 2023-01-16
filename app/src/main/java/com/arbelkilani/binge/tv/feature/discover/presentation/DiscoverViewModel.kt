package com.arbelkilani.binge.tv.feature.discover.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.discover.domain.mapper.TvEntityMapper
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.*
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getTrendingUseCase: GetTrendingUseCase,
    private val getFreeUseCase: GetFreeUseCase
) : BaseStateViewModel<DiscoverViewState>(initialState = DiscoverViewState.Start) {

    private val _trending = MutableStateFlow(PagingData.empty<Tv>())
    val trending: StateFlow<PagingData<Tv>> = _trending

    private val _free = MutableStateFlow(PagingData.empty<Tv>())
    val free: StateFlow<PagingData<Tv>> = _free

    @Inject
    lateinit var mapper: TvEntityMapper

    suspend fun init() {
        free()
        trending()
    }

    private fun trending() = viewModelScope.launch {
        getTrendingUseCase.invoke()
            .cachedIn(viewModelScope)
            .collectLatest { data ->
                updateState { DiscoverViewState.Loaded }
                _trending.value = data.map { mapper.map(it) }
            }
    }

    private suspend fun free() = viewModelScope.launch {
        getFreeUseCase.invoke()
            .cachedIn(viewModelScope)
            .collectLatest { data ->
                updateState { DiscoverViewState.Loaded }
                _free.value = data.map { mapper.map(it) }
            }
    }
}