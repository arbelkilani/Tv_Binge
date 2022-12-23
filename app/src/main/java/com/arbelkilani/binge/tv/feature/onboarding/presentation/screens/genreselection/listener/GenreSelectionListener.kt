package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.genreselection.listener

import com.arbelkilani.binge.tv.common.domain.model.GenreEntity

interface GenreSelectionListener {
    fun onGenreClicked(genre: GenreEntity)
}