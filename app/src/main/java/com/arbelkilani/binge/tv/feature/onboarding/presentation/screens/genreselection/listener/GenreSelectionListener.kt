package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.genreselection.listener

import com.arbelkilani.binge.tv.common.domain.entities.GenreEntity

interface GenreSelectionListener {
    fun onGenreClicked(genre: GenreEntity)
}