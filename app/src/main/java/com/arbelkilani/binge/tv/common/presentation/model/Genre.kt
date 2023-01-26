package com.arbelkilani.binge.tv.common.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val id: Int,
    val name: String,
    val isFavorite: Boolean,
    val isSelected: Boolean = false
) : Parcelable