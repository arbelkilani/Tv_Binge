package com.arbelkilani.binge.tv.common.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configuration_table")
data class ApiConfigurationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val url: String,
    val backdrop: Image,
    val logo: Image,
    val poster: Image,
    val profile: Image
)

data class Image(
    val original: String? = "",
    val small: String? = "",
    val medium: String? = "",
    val large: String? = ""
)