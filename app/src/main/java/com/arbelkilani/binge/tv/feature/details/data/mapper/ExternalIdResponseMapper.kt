package com.arbelkilani.binge.tv.feature.details.data.mapper

import com.arbelkilani.binge.tv.feature.details.data.entities.ExternalIdResponse
import com.arbelkilani.binge.tv.feature.details.domain.entities.ExternalIdEntity
import javax.inject.Inject

class ExternalIdResponseMapper @Inject constructor() {
    fun map(response: ExternalIdResponse) = ExternalIdEntity(
        id = response.id,
        facebook = response.facebook,
        twitter = response.twitter,
        instagram = response.instagram
    )
}