package com.arbelkilani.binge.tv.feature.details.data.mapper

import com.arbelkilani.binge.tv.feature.details.data.entities.ContentRatingsResponse
import com.arbelkilani.binge.tv.feature.details.domain.entities.ContentRatingsEntity
import javax.inject.Inject

class ContentRatingsResponseMapper @Inject constructor() {
    fun map(response: ContentRatingsResponse) = ContentRatingsEntity(
        isoCode = response.isoCode,
        rating = response.rating
    )
}
