package com.arbelkilani.binge.tv.feature.details.presentation.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeToAir(
    val name: String,
    val story: String,
    val episodeNumber: Int,
    val seasonNumber: Int
) : Parcelable