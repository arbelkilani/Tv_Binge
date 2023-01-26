package com.arbelkilani.binge.tv.feature.search.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.base.viewmodel.BaseStateViewModel
import com.arbelkilani.binge.tv.common.domain.usecase.ObserveNetworkReachabilityUseCase
import com.arbelkilani.binge.tv.common.presentation.model.Genre
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv
import com.arbelkilani.binge.tv.feature.search.domain.usecase.GetGenresUseCase
import com.arbelkilani.binge.tv.feature.search.presentation.model.SearchViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getNetworkReachabilityUseCase: ObserveNetworkReachabilityUseCase,
    private val getGenreUseCase: GetGenresUseCase
) :
    BaseStateViewModel<SearchViewState>(initialState = SearchViewState.Start) {

    private val _genres = MutableStateFlow(emptyList<Genre>())
    val genres: StateFlow<List<Genre>> = _genres

    private var current: Boolean? = null

    init {
        observeNetwork()
    }

    suspend fun start() {
        genres()
    }

    private fun genres() = viewModelScope.launch {
        getGenreUseCase.invoke()
            .collectLatest { data ->
                updateState { SearchViewState.Loaded }
                _genres.value = data
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
        if (networkState) updateState { SearchViewState.Start }
        else updateState { SearchViewState.Error(IOException()) }
    }
}