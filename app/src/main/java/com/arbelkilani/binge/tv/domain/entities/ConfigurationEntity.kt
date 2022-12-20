package com.arbelkilani.binge.tv.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configuration_table")
data class ConfigurationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val url: String,
    val backdrop: Image,
    val logo: Image,
    val poster: Image
)

data class Image(
    val original: String?,
    val small: String?,
    val medium: String?,
    val large: String?
)