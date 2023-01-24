package com.arbelkilani.binge.tv.feature.details.domain.mapper

import com.arbelkilani.binge.tv.feature.details.domain.entities.ExternalIdEntity
import com.arbelkilani.binge.tv.feature.details.presentation.entities.ExternalId
import javax.inject.Inject

class ExternalIdEntityMapper @Inject constructor() {
    fun map(entity: ExternalIdEntity) = ExternalId(
        id = entity.id,
        facebook = entity.facebook.orEmpty(),
        instagram = entity.instagram.orEmpty(),
        twitter = entity.twitter.orEmpty()
    )
}