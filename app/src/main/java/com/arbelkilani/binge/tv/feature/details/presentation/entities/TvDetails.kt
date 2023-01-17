package com.arbelkilani.binge.tv.feature.details.presentation.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvDetails(
    val name: String,
    val status: String,
    val story: String,
    val genres: List<String>,
    val vote: String
) : Parcelable