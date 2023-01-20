package com.arbelkilani.binge.tv.common.domain.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "genre_table")
data class GenreEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val isFavorite: Boolean
): Parcelable