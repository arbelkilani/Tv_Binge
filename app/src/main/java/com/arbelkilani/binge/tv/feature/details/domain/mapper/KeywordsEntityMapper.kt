package com.arbelkilani.binge.tv.feature.details.domain.mapper

import com.arbelkilani.binge.tv.feature.details.domain.entities.KeywordsEntity
import com.arbelkilani.binge.tv.feature.details.presentation.entities.Keywords
import javax.inject.Inject

class KeywordsEntityMapper @Inject constructor() {
    fun map(entity: KeywordsEntity) = Keywords(
        id = entity.id, name = "#${entity.name.replace(" ", "_")}"
    )
}