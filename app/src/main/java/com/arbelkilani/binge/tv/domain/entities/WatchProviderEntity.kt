package com.arbelkilani.binge.tv.domain.entities

data class WatchProviderEntity(
    val id: Int,
    val name: String,
    val logo: String,
    val priority: Int,
    val isFavorite: Boolean
)