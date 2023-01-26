package com.arbelkilani.binge.tv.feature.discover.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arbelkilani.binge.tv.common.base.viewmodel.BaseStateViewModel
import com.arbelkilani.binge.tv.common.domain.usecase.GetGenresUseCase
import com.arbelkilani.binge.tv.common.domain.usecase.GetProvidersUseCase
import com.arbelkilani.binge.tv.common.domain.usecase.ObserveNetworkReachabilityUseCase
import com.arbelkilani.binge.tv.common.presentation.model.Genre
import com.arbelkilani.binge.tv.common.presentation.model.Provider
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetFilteredShowsUseCase
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetShowsUseCase
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import com.arbelkilani.binge.tv.feature.home.presentation.model.Tv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getNetworkReachabilityUseCase: ObserveNetworkReachabilityUseCase,
    private val getGenreUseCase: GetGenresUseCase,
    private val getProvidersUseCase: GetProvidersUseCase,
    private val getShowsUseCase: GetShowsUseCase,
    private val getFilteredShowsUseCase: GetFilteredShowsUseCase
) :
    BaseStateViewModel<DiscoverViewState>(initialState = DiscoverViewState.Start) {

    private val _genres = MutableStateFlow(emptyList<Genre>())
    val genres: StateFlow<List<Genre>> = _genres

    private val _providers = MutableStateFlow(emptyList<Provider>())
    val providers: StateFlow<List<Provider>> = _providers

    private val _shows = MutableStateFlow(PagingData.empty<Tv>())
    val shows: StateFlow<PagingData<Tv>> = _shows

    private var current: Boolean? = null
    private val selectedGenres = mutableListOf<Genre>()
    private val selectedProviders = mutableListOf<Provider>()

    init {
        observeNetwork()
    }

    fun start() {
        genres()
        providers()
        shows()
    }

    private fun genres() = viewModelScope.launch {
        getGenreUseCase.invoke()
            .collectLatest { data ->
                updateState { DiscoverViewState.Loaded }
                _genres.value = data
            }
    }

    private fun providers(filter: CharSequence = "") = viewModelScope.launch {
        getProvidersUseCase.invoke()
            .collectLatest { data ->
                updateState { DiscoverViewState.Loaded }
                _providers.value = data.filter { it.name.contains(filter, ignoreCase = true) }
            }
    }

    private fun shows() = viewModelScope.launch {
        getShowsUseCase.invoke()
            .cachedIn(viewModelScope)
            .collectLatest { data ->
                updateState { DiscoverViewState.Loaded }
                _shows.value = data
            }
    }

    private fun filter(
        genres: MutableList<Genre>,
        providers: MutableList<Provider>
    ) = viewModelScope.launch {
        getFilteredShowsUseCase.invoke(genres, providers)
            .cachedIn(viewModelScope)
            .collectLatest { data ->
                updateState { DiscoverViewState.Loaded }
                _shows.value = data
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
        if (networkState) updateState { DiscoverViewState.Start }
        else updateState { DiscoverViewState.Error(IOException()) }
    }

    fun filterProviders(filter: CharSequence) {
        providers(filter)
    }

    fun setGenres(genre: Genre) {
        selectedGenres.add(genre)
        filter(selectedGenres, selectedProviders)
    }

    fun setProvider(provider: Provider) {
        selectedProviders.add(provider)
        filter(selectedGenres, selectedProviders)
    }
}