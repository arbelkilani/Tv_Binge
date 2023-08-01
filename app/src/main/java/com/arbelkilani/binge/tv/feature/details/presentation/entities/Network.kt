package com.arbelkilani.binge.tv.feature.details.presentation.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Network(
    val id: Int,
    val name: String,
    val logo: String
) : Parcelable