package com.arbelkilani.binge.tv.feature.details.presentation.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvDetails(
    val status: String
) : Parcelable