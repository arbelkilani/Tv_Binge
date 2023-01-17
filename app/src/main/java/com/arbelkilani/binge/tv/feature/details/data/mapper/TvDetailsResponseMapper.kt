package com.arbelkilani.binge.tv.feature.details.data.mapper

import com.arbelkilani.binge.tv.feature.details.data.entities.TvDetailsResponse
import com.arbelkilani.binge.tv.feature.details.domain.entities.TvDetailsEntity
import javax.inject.Inject

class TvDetailsResponseMapper @Inject constructor() {

    @Inject
    lateinit var networkResponseMapper: NetworkResponseMapper

    @Inject
    lateinit var episodeToAirResponseMapper: EpisodeToAirResponseMapper

    fun map(response: TvDetailsResponse) = TvDetailsEntity(
        name = response.name,
        overview = response.overview,
        genres = response.genres,
        status = response.status,
        voteAverage = response.voteAverage,
        networks = response.networks.map { networkResponseMapper.map(it) },
        episodeToAir = response.nextEpisodeToAir?.let { episodeToAirResponseMapper.map(it) },
        tagline = response.tagline
    )
}