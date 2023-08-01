package com.arbelkilani.binge.tv.common.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watch_provider_table")
data class WatchProviderEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val logo: String,
    val priority: Int,
    val isFavorite: Boolean
)