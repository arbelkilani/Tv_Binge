package com.arbelkilani.binge.tv.feature.details.domain.entities

import com.arbelkilani.binge.tv.common.data.response.GenreResponse
import java.time.LocalDate

data class TvDetailsEntity(
    val name: String,
    val overview: String,
    val genreResponses: List<GenreResponse>,
    val status: String,
    val voteAverage: Float,
    val networks: List<NetworkEntity>,
    val episodeToAir: EpisodeToAirEntity?,
    val tagline: String,
    val createdBy: List<String>,
    val firstAirDate: LocalDate?,
    val companies: List<String>
)