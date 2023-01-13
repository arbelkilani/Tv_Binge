package com.arbelkilani.binge.tv.feature.details.domain.mapper

import com.arbelkilani.binge.tv.feature.details.domain.entities.TvDetailsEntity
import com.arbelkilani.binge.tv.feature.details.presentation.entities.TvDetails
import javax.inject.Inject

class TvDetailsEntityMapper @Inject constructor() {

    fun map(entity: TvDetailsEntity) = TvDetails(
        status = entity.status
    )
}