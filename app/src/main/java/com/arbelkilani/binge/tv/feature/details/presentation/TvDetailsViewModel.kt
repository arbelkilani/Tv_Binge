package com.arbelkilani.binge.tv.feature.details.presentation

import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.common.presentation.model.Person
import com.arbelkilani.binge.tv.feature.details.domain.usecase.GetCastsUseCase
import com.arbelkilani.binge.tv.feature.details.domain.usecase.GetKeywordsUseCase
import com.arbelkilani.binge.tv.feature.details.domain.usecase.GetTvDetailsDataUseCase
import com.arbelkilani.binge.tv.feature.details.presentation.entities.Keywords
import com.arbelkilani.binge.tv.feature.details.presentation.entities.TvDetails
import com.arbelkilani.binge.tv.feature.details.presentation.model.TvDetailsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _casts = MutableStateFlow(emptyList<Person>())
    val casts: StateFlow<List<Person>> = _casts

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