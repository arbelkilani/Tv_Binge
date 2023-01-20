package com.arbelkilani.binge.tv.feature.details.domain.entities

import com.arbelkilani.binge.tv.common.data.response.Genre
import java.time.LocalDate

data class TvDetailsEntity(
    val name: String,
    val overview: String,
    val genres: List<Genre>,
    val status: String,
    val voteAverage: Float,
    val networks: List<NetworkEntity>,
    val episodeToAir: EpisodeToAirEntity?,
    val tagline: String,
    val createdBy: List<String>,
    val firstAirDate: LocalDate?,
    val companies: List<String>
)