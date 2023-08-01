package com.arbelkilani.binge.tv.feature.details.data.mapper

import com.arbelkilani.binge.tv.feature.details.data.entities.KeywordsResponse
import com.arbelkilani.binge.tv.feature.details.domain.entities.KeywordsEntity
import javax.inject.Inject

class KeywordsResponseMapper @Inject constructor() {
    fun map(response: KeywordsResponse) = KeywordsEntity(
        id = response.id,
        name = response.name
    )
}