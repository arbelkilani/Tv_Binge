package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.genreselection

import androidx.lifecycle.viewModelScope
import com.arbelkilani.binge.tv.common.base.BaseStateViewModel
import com.arbelkilani.binge.tv.common.domain.entities.GenreEntity
import com.arbelkilani.binge.tv.feature.onboarding.domain.usecase.GetGenresUseCase
import com.arbelkilani.binge.tv.feature.onboarding.domain.usecase.UpdateGenreUseCase
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.genreselection.model.GenreSelectionViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreSelectionViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val updateGenreUseCase: UpdateGenreUseCase
) :
    BaseStateViewModel<GenreSelectionViewState>(
        initialState = GenreSelectionViewState.Start
    ) {

    fun load() {
        viewModelScope.launch {
            try {
                getGenresUseCase.invoke().collectLatest { list ->
                    updateState { GenreSelectionViewState.Loaded(list) }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                updateState { GenreSelectionViewState.Error(exception) }
            }
        }
    }

    fun updateGenre(genre: GenreEntity) {
        viewModelScope.launch {
            updateGenreUseCase.invoke(genre)
        }
    }
}