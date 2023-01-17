package com.arbelkilani.binge.tv.feature.details.data.mapper

import com.arbelkilani.binge.tv.feature.details.data.entities.EpisodeToAirResponse
import com.arbelkilani.binge.tv.feature.details.domain.entities.EpisodeToAirEntity
import javax.inject.Inject

class EpisodeToAirResponseMapper @Inject constructor() {
    fun map(response: EpisodeToAirResponse) = EpisodeToAirEntity(
        name = response.name
    )
}