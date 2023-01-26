package com.arbelkilani.binge.tv.feature.home.domain.entity

import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.common.domain.entity.ProviderEntity

data class TvEntity(
    val id: Int,
    val name: String,
    val poster: String?,
    val backdrop: String?,
    val genres: List<GenreEntity>,
    val voteAverage: Float,
    val firstAirDate: String,
    val providers: List<ProviderEntity>
)