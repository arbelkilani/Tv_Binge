package com.arbelkilani.binge.tv.feature.details.domain.mapper

import com.arbelkilani.binge.tv.feature.details.domain.entities.EpisodeToAirEntity
import com.arbelkilani.binge.tv.feature.details.presentation.entities.EpisodeToAir
import javax.inject.Inject

class EpisodeToAirEntityMapper @Inject constructor() {
    fun map(entity: EpisodeToAirEntity) = EpisodeToAir(
        name = entity.name
    )
}