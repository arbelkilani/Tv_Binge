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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getDiscoverDataUseCase: GetDiscoverDataUseCase,
    private val getFavoriteProvidersUseCase: GetFavoriteProvidersUseCase,
    private val getFavoriteGenresUseCase: GetFavoriteGenresUseCase,
    private val getTrendingUseCase: GetTrendingUseCase,
    private val getFreeUseCase: GetFreeUseCase
) : BaseStateViewModel<DiscoverViewState>(initialState = DiscoverViewState.Start) {

    private val _favoriteProviders = MutableStateFlow("")
    val favoriteProviders: StateFlow<String> = _favoriteProviders

    private val _favoriteGenres = MutableStateFlow("")
    val favoriteGenres: StateFlow<String> = _favoriteGenres

    private val _trending = MutableStateFlow(PagingData.empty<Tv>())
    val trending: StateFlow<PagingData<Tv>> = _trending

    private val _free = MutableStateFlow(PagingData.empty<Tv>())
    val free: StateFlow<PagingData<Tv>> = _free

    @Inject
    lateinit var mapper: TvEntityMapper

    init {
        viewModelScope.launch {
            free()
        }
    }

    suspend fun init() {
        updateState { DiscoverViewState.Loading }
        //trending()

        /*viewModelScope.launch(Dispatchers.IO) {
            getDiscoverDataUseCase.invoke(viewModelScope).flowOn(Dispatchers.IO)
                .collectLatest { data ->
                    updateState {
                        DiscoverViewState.Data(
                            trending = data.trending,
                            startingThisMonth = data.startingThisMonth,
                            basedOnProvider = data.basedOnProvider,
                            free = data.free,
                            providers = data.providers,
                            genres = data.genres,
                            basedOnGenres = data.basedOnGenres,
                            upcoming = data.upcoming
                        )
                    }
                }
        }*/

        /*viewModelScope.launch {
            getFavoriteProvidersUseCase.invoke().collectLatest { list ->
                list?.let {
                    _favoriteProviders.value =
                        it.joinToString(separator = ", ") { item -> item.name }
                }
            }
        }

        viewModelScope.launch {
            getFavoriteGenresUseCase.invoke().collectLatest { list ->
                list?.let {
                    _favoriteGenres.value = it.joinToString(separator = ", ") { item -> item.name }
                }
            }
        } */
    }

    private suspend fun trending() {
        viewModelScope.launch(Dispatchers.IO) {
            getTrendingUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
                .collect { data ->
                    _trending.value = data.map { mapper.map(it) }
                }
        }
    }

    private suspend fun free() = getFreeUseCase.invoke()
        .cachedIn(viewModelScope)
        .collectLatest { data ->
            updateState { DiscoverViewState.Loaded }
            _free.value = data.map { mapper.map(it) }
        }
}