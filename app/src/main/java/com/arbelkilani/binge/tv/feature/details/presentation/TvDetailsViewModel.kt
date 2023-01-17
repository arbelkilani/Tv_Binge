package com.arbelkilani.binge.tv.feature.details.presentation

import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.details.domain.usecase.GetTvDetailsDataUseCase
import com.arbelkilani.binge.tv.feature.details.presentation.model.TvDetailsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvDetailsViewModel @Inject constructor(
    private val tvDetailsDataUseCase: GetTvDetailsDataUseCase
) :
    BaseStateViewModel<TvDetailsViewState>(initialState = TvDetailsViewState.Start) {

    suspend fun init(id: Int) {
        updateState { TvDetailsViewState.Loading }
        getDetails(id)
    }

    private suspend fun getDetails(id: Int) = viewModelScope.launch {
        tvDetailsDataUseCase.invoke(id)
            .onStart {
                updateState { TvDetailsViewState.Loading }
            }.catch { throwable ->
                updateState { TvDetailsViewState.Error(throwable) }
            }.collect { data ->
                updateState { TvDetailsViewState.Data(data) }
            }
    }
}