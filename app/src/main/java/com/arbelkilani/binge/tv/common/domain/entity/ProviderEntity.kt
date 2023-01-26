package com.arbelkilani.binge.tv.common.domain.entity

data class ProviderEntity(
    val id: Int,
    val name: String,
    val logo: String?,
    val type: String? = "",
    val link: String? = "",
    val isFavorite: Boolean? = false
)