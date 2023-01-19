package com.arbelkilani.binge.tv.feature.discover.presentation.entities

import android.os.Parcelable
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tv(
    val id: Int,
    val name: String,
    val poster: String?,
    val backdrop: String?,
    val genres: List<GenreEntity>,
    val voteAverage: Float,
    val firstAirDate: String
) : Parcelable