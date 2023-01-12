package com.arbelkilani.binge.tv.feature.details.presentation

import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.details.domain.usecase.GetTvDetailsDataUseCase
import com.arbelkilani.binge.tv.feature.details.presentation.model.TvDetailsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvDetailsViewModel @Inject constructor(
    private val tvDetailsDataUseCase: GetTvDetailsDataUseCase
) :
    BaseStateViewModel<TvDetailsViewState>(initialState = TvDetailsViewState.Start) {

    suspend fun init(id: Int) {
        tvDetailsDataUseCase.invoke(id)
    }
}