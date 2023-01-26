package com.arbelkilani.binge.tv.feature.discover.presentation

import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.common.base.viewmodel.BaseStateViewModel
import com.arbelkilani.binge.tv.common.domain.usecase.GetGenresUseCase
import com.arbelkilani.binge.tv.common.domain.usecase.GetProvidersUseCase
import com.arbelkilani.binge.tv.common.domain.usecase.ObserveNetworkReachabilityUseCase
import com.arbelkilani.binge.tv.common.presentation.model.Genre
import com.arbelkilani.binge.tv.common.presentation.model.Provider
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getNetworkReachabilityUseCase: ObserveNetworkReachabilityUseCase,
    private val getGenreUseCase: GetGenresUseCase,
    private val getProvidersUseCase: GetProvidersUseCase
) :
    BaseStateViewModel<DiscoverViewState>(initialState = DiscoverViewState.Start) {

    private val _genres = MutableStateFlow(emptyList<Genre>())
    val genres: StateFlow<List<Genre>> = _genres

    private val _providers = MutableStateFlow(emptyList<Provider>())
    val providers: StateFlow<List<Provider>> = _providers

    private var current: Boolean? = null

    init {
        observeNetwork()
    }

    fun start() {
        genres()
        providers()
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


}