package com.arbelkilani.binge.tv.common.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "certification_table")
data class CertificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val meaning: String,
    val order: Int
)