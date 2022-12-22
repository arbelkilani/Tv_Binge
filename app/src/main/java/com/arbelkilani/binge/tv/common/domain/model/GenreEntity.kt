package com.arbelkilani.binge.tv.common.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre_table")
data class GenreEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val isFavorite: Boolean
)