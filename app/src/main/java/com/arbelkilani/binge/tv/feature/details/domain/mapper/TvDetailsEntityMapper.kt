package com.arbelkilani.binge.tv.feature.details.domain.mapper

import com.arbelkilani.binge.tv.feature.details.domain.entities.TvDetailsEntity
import com.arbelkilani.binge.tv.feature.details.presentation.entities.TvDetails
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TvDetailsEntityMapper @Inject constructor() {

    @Inject
    lateinit var networkEntityMapper: NetworkEntityMapper

    @Inject
    lateinit var episodeToAirEntityMapper: EpisodeToAirEntityMapper

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM yyyy")

    suspend fun map(entity: TvDetailsEntity) = TvDetails(
        name = entity.name,
        story = entity.overview,
        genres = entity.genres.map { it.name },
        status = entity.status,
        vote = if (entity.voteAverage == 0f) "" else DecimalFormat("0.#").format(entity.voteAverage),
        networks = entity.networks.map { networkEntityMapper.map(it) },
        episodeToAir = entity.episodeToAir?.let { episodeToAirEntityMapper.map(it) },
        tagline = entity.tagline,
        createdBy = entity.createdBy.joinToString(separator = ", "),
        firstAirDate = entity.firstAirDate?.format(formatter)?.replaceFirstChar(Char::uppercase),
        productionCompanies = entity.companies.joinToString(separator = ", ")
    )
}