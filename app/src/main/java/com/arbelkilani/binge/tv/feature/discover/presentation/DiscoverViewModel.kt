package com.arbelkilani.binge.tv.feature.discover.presentation

import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetDiscoverDataUseCase
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetFavoriteProvidersUseCase
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getDiscoverDataUseCase: GetDiscoverDataUseCase,
    private val getFavoriteProvidersUseCase: GetFavoriteProvidersUseCase
) : BaseStateViewModel<DiscoverViewState>(initialState = DiscoverViewState.Start) {

    private val _favoriteProviders = MutableStateFlow("")
    val favoriteProviders: StateFlow<String> = _favoriteProviders

    suspend fun init() {
        updateState { DiscoverViewState.Loading }
        viewModelScope.launch(Dispatchers.IO) {
            getDiscoverDataUseCase.invoke(viewModelScope).flowOn(Dispatchers.IO)
                .collectLatest { data ->
                    updateState {
                        DiscoverViewState.Data(
                            trending = data.trending,
                            startingThisMonth = data.startingThisMonth,
                            basedOnProvider = data.basedOnProvider,
                            free = data.free,
                            providers = data.providers,
                            genres = data.genres
                        )
                    }
                }
        }

        viewModelScope.launch {
            getFavoriteProvidersUseCase.invoke().collectLatest { list ->
                list?.let {
                    _favoriteProviders.value = it.joinToString(separator = ", ") { it.name }
                }
            }
        }
    }
}