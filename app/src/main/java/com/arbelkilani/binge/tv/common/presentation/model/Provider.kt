package com.arbelkilani.binge.tv.common.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Provider(
    val id: Int,
    val name: String,
    val logo: String,
    val type: String?,
    val link: String?,
    val isFavorite: Boolean?
) : Parcelable