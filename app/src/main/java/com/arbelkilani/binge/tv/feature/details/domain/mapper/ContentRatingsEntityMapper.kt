package com.arbelkilani.binge.tv.feature.details.domain.mapper

import com.arbelkilani.binge.tv.feature.details.domain.entities.ContentRatingsEntity
import com.arbelkilani.binge.tv.feature.details.presentation.entities.ContentRating
import javax.inject.Inject

class ContentRatingsEntityMapper @Inject constructor() {
    fun map(entity: ContentRatingsEntity) = ContentRating(
        isoCode = entity.isoCode,
        rating = entity.rating
    )
}