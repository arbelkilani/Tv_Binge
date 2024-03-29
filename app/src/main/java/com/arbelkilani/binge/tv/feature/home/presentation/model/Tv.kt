package com.arbelkilani.binge.tv.feature.home.presentation.model

import android.os.Parcelable
import com.arbelkilani.binge.tv.common.presentation.model.Genre
import com.arbelkilani.binge.tv.common.presentation.model.Provider
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tv(
    val id: Int,
    val name: String,
    val poster: String?,
    val backdrop: String?,
    val genres: List<Genre>,
    val voteAverage: Float,
    val firstAirDate: String,
    val providers: List<Provider>
) : Parcelable