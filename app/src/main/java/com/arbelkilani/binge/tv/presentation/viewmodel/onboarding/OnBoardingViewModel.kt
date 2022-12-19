package com.arbelkilani.binge.tv.presentation.viewmodel.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.domain.usecase.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            getGenresUseCase.execute()
        }
    }

    fun execute() {
        viewModelScope.launch {
            getGenresUseCase.execute()
        }
    }
}