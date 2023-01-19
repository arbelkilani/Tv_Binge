package com.arbelkilani.binge.tv.feature.discover.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.common.domain.usecase.ObserveNetworkReachabilityUseCase
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.*
import com.arbelkilani.binge.tv.feature.discover.presentation.entities.DiscoverViewState
import com.arbelkilani.binge.tv.feature.discover.presentation.entities.Person
import com.arbelkilani.binge.tv.feature.discover.presentation.entities.Tv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getNetworkReachabilityUseCase: ObserveNetworkReachabilityUseCase,
    private val getTrendingUseCase: GetTrendingUseCase,
    private val getUpcomingUseCase: GetUpcomingUseCase,
    private val getTalkShowsUseCase: GetTalkShowsUseCase,
    private val getDocumentariesUseCase: GetDocumentariesUseCase,
    private val getTrendingPersonUseCase: GetTrendingPersonUseCase
) : BaseStateViewModel<DiscoverViewState>(initialState = DiscoverViewState.Start) {

    private val _trending = MutableStateFlow(PagingData.empty<Tv>())
    val trending: StateFlow<PagingData<Tv>> = _trending

    private val _upcoming = MutableStateFlow(PagingData.empty<Tv>())
    val upcoming: StateFlow<PagingData<Tv>> = _upcoming

    private val _talkShows = MutableStateFlow(PagingData.empty<Tv>())
    val talkShows: StateFlow<PagingData<Tv>> = _talkShows

    private val _documentaries = MutableStateFlow(PagingData.empty<Tv>())
    val documentaries: StateFlow<PagingData<Tv>> = _documentaries

    private val _persons = MutableStateFlow(PagingData.empty<Person>())
    val persons: StateFlow<PagingData<Person>> = _persons

    suspend fun init() {

        val test = getNetworkReachabilityUseCase.invoke()
        Log.i("TAG**", "test = ${test.value}")
        free()
        trending()
        talkShows()
        documentaries()
        persons()
    }

    private fun trending() = viewModelScope.launch {
        getTrendingUseCase.invoke()
            .cachedIn(viewModelScope)
            .collectLatest { data ->
                updateState { DiscoverViewState.Loaded }
                _trending.value = data
            }
    }

    private suspend fun free() = viewModelScope.launch {
        getUpcomingUseCase.invoke()
            .cachedIn(viewModelScope)
            .collectLatest { data ->
                updateState { DiscoverViewState.Loaded }
                _upcoming.value = data
            }
    }

    private suspend fun talkShows() = viewModelScope.launch {
        getTalkShowsUseCase.invoke()
            .cachedIn(viewModelScope)
            .collectLatest { data ->
                updateState { DiscoverViewState.Loaded }
                _talkShows.value = data
            }
    }

    private suspend fun documentaries() = viewModelScope.launch {
        getDocumentariesUseCase.invoke()
            .cachedIn(viewModelScope)
            .collectLatest { data ->
                updateState { DiscoverViewState.Loaded }
                _documentaries.value = data
            }
    }

    private suspend fun persons() = viewModelScope.launch {
        getTrendingPersonUseCase.invoke()
            .cachedIn(viewModelScope)
            .collectLatest { data ->
                updateState { DiscoverViewState.Loaded }
                _persons.value = data
            }
    }
}