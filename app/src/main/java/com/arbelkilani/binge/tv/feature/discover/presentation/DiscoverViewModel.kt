package com.arbelkilani.binge.tv.feature.discover.presentation

import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.feature.discover.domain.usecase.GetTrendingUseCase
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getTrendingUseCase: GetTrendingUseCase
) : BaseStateViewModel<DiscoverViewState>(initialState = DiscoverViewState.Start) {

    fun init() {
        updateState { DiscoverViewState.Loading }
        viewModelScope.launch {
            try {
                getTrendingUseCase.invoke().collectLatest { list ->
                    updateState {
                        DiscoverViewState.TrendingLoaded(data = list)
                    }
                }
            } catch (exception: Exception) {
                handleError(exception)
            }
        }
    }

    override fun handleError(exception: Exception) {
        when (exception) {
            is HttpException -> updateState { DiscoverViewState.HttpException(exception) }
            is IOException -> updateState { DiscoverViewState.IOException(exception) }
            else -> updateState { DiscoverViewState.UnknownException(exception) }
        }
    }
}