package com.arbelkilani.binge.tv.feature.details.data.mapper

import com.arbelkilani.binge.tv.feature.details.data.entities.CreditsResponse
import com.arbelkilani.binge.tv.feature.details.domain.entities.CastEntity
import javax.inject.Inject

class CastResponseMapper @Inject constructor() {
    fun map(response: CreditsResponse) = CastEntity(
        id = response.id,
        name = response.name,
        posterPath = response.profilePath
    )
}