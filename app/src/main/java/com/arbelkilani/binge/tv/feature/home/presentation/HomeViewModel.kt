package com.arbelkilani.binge.tv.feature.home.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arbelkilani.binge.tv.common.base.viewmodel.BaseStateViewModel
import com.arbelkilani.binge.tv.common.domain.usecase.ObserveNetworkReachabilityUseCase
import com.arbelkilani.binge.tv.common.presentation.model.Person
import com.arbelkilani.binge.tv.feature.home.domain.usecase.*
import com.arbelkilani.binge.tv.feature.home.presentation.model.HomeViewState
import com.arbelkilani.binge.tv.feature.home.presentation.model.Tv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNetworkReachabilityUseCase: ObserveNetworkReachabilityUseCase,
    private val getTrendingUseCase: GetTrendingUseCase,
    private val getUpcomingUseCase: GetUpcomingUseCase,
    private val getTalkShowsUseCase: GetTalkShowsUseCase,
    private val getDocumentariesUseCase: GetDocumentariesUseCase,
    private val getTrendingPersonUseCase: GetTrendingPersonUseCase,
    private val getFreeUseCase: GetFreeUseCase
) : BaseStateViewModel<HomeViewState>(initialState = HomeViewState.Start) {

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

    private val _free = MutableStateFlow(PagingData.empty<Tv>())
    val free: StateFlow<PagingData<Tv>> = _free

    private var current: Boolean? = null

    init {
        observeNetwork()
    }

    suspend fun start() {
        upcoming()
        trending()
        talkShows()
        documentaries()
        persons()
        free()
    }

    private fun trending() = viewModelScope.launch {
        getTrendingUseCase.invoke()
            .cachedIn(viewModelScope)
            .collectLatest { data ->
                updateState { HomeViewState.Loaded }
                _trending.value = data
            }
    }

    private suspend fun upcoming() = viewModelScope.launch {
        getUpcomingUseCase.invoke()
            .cachedIn(viewModelScope)
            .collectLatest { data ->
                updateState { HomeViewState.Loaded }
                _upcoming.value = data
            }
    }

    private suspend fun free() = viewModelScope.launch {
        getFreeUseCase.invoke()
            .cachedIn(viewModelScope)
            .collectLatest { data ->
                updateState { HomeViewState.Loaded }
                _free.value = data
            }
    }

    private suspend fun talkShows() = viewModelScope.launch {
        getTalkShowsUseCase.invoke()
            .cachedIn(viewModelScope)
            .collectLatest { data ->
                updateState { HomeViewState.Loaded }
                _talkShows.value = data
            }
    }

    private suspend fun documentaries() = viewModelScope.launch {
        getDocumentariesUseCase.invoke()
            .cachedIn(viewModelScope)
            .collectLatest { data ->
                updateState { HomeViewState.Loaded }
                _documentaries.value = data
            }
    }

    private suspend fun persons() = viewModelScope.launch {
        getTrendingPersonUseCase.invoke()
            .cachedIn(viewModelScope)
            .collectLatest { data ->
                updateState { HomeViewState.Loaded }
                _persons.value = data
            }
    }

    private fun observeNetwork() {
        getNetworkReachabilityUseCase.invoke().observeForever { networkState ->
            current?.let { value ->
                if (value != networkState) retry(networkState)
            } ?: run {
                retry(networkState)
            }
            current = networkState
        }
    }

    private fun retry(networkState: Boolean) {
        if (networkState) updateState { HomeViewState.Start }
        else updateState { HomeViewState.Error(IOException()) }
    }
}