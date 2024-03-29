package com.arbelkilani.binge.tv.common.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configuration_table")
data class ConfigurationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val url: String
)