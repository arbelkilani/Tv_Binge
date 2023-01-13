package com.arbelkilani.binge.tv.feature.details.data.mapper

import com.arbelkilani.binge.tv.feature.details.data.entities.TvDetailsResponse
import com.arbelkilani.binge.tv.feature.details.domain.entities.TvDetailsEntity
import javax.inject.Inject

class TvDetailsResponseMapper @Inject constructor() {

    suspend fun map(response: TvDetailsResponse) = TvDetailsEntity(
        status = response.status
    )
}