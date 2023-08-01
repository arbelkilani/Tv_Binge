package com.arbelkilani.binge.tv.feature.details.domain.entities

data class EpisodeToAirEntity(
    val name: String,
    val story: String,
    val episodeNumber: Int,
    val seasonNumber: Int,
)