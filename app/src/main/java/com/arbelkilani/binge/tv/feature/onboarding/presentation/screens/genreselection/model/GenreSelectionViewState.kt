package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.genreselection.model

import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity

sealed class GenreSelectionViewState {
    object Start : GenreSelectionViewState()
    data class Error(val exception: Exception) : GenreSelectionViewState()
    data class Loaded(val list: List<GenreEntity>) : GenreSelectionViewState()
}