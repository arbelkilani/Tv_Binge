package com.arbelkilani.binge.tv.feature.discover.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Provider(
    val id: Int,
    val name: String,
    val logo: String?,
    val type: String
) : Parcelable