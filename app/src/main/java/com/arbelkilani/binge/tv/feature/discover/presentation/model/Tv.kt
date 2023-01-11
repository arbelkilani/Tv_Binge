package com.arbelkilani.binge.tv.feature.discover.presentation.model

import com.arbelkilani.binge.tv.common.domain.model.GenreEntity

data class Tv(
    val id: Int,
    val name: String,
    val poster: String?,
    val backdrop: String?,
    val genres: List<GenreEntity>,
    val voteAverage: Float,
    val firstAirDate: String
)