package com.arbelkilani.binge.tv.feature.details.presentation

import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.details.domain.usecase.GetCastsUseCase
import com.arbelkilani.binge.tv.feature.details.domain.usecase.GetKeywordsUseCase
import com.arbelkilani.binge.tv.feature.details.domain.usecase.GetTvDetailsDataUseCase
import com.arbelkilani.binge.tv.feature.details.presentation.entities.Cast
import com.arbelkilani.binge.tv.feature.details.presentation.entities.Keywords
import com.arbelkilani.binge.tv.feature.details.presentation.entities.TvDetails
import com.arbelkilani.binge.tv.feature.details.presentation.model.TvDetailsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvDetailsViewModel @Inject constructor(
    private val tvDetailsDataUseCase: GetTvDetailsDataUseCase,
    private val getKeywordsUseCase: GetKeywordsUseCase,
    private val getCastsUseCase: GetCastsUseCase
) :
    BaseStateViewModel<TvDetailsViewState>(initialState = TvDetailsViewState.Start) {

    private val _details = MutableStateFlow<TvDetails?>(null)
    val details: StateFlow<TvDetails?> = _details

    private val _keywords = MutableStateFlow(emptyList<Keywords>())
    val keywords: StateFlow<List<Keywords>> = _keywords

    private val _casts = MutableStateFlow(emptyList<Cast>())
    val casts: StateFlow<List<Cast>> = _casts

    suspend fun init(id: Int) {
        getDetails(id)
        getKeywords(id)
        getCasts(id)
    }

    private suspend fun getDetails(id: Int) = viewModelScope.launch {
        tvDetailsDataUseCase.invoke(id)
            .collectLatest { data ->
                updateState { TvDetailsViewState.Loaded }
                _details.value = data
            }
    }

    private suspend fun getKeywords(id: Int) = viewModelScope.launch {
        getKeywordsUseCase.invoke(id)
            .collectLatest { data ->
                updateState { TvDetailsViewState.Loaded }
                _keywords.value = data
            }
    }

    private suspend fun getCasts(id: Int) = viewModelScope.launch {
        getCastsUseCase.invoke(id)
            .collectLatest { data ->
                updateState { TvDetailsViewState.Loaded }
                _casts.value = data
            }
    }
}