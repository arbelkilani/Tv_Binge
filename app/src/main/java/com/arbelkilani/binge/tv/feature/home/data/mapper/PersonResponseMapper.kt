package com.arbelkilani.binge.tv.feature.home.data.mapper

import com.arbelkilani.binge.tv.feature.home.data.response.PersonResponse
import com.arbelkilani.binge.tv.feature.home.domain.entity.PersonEntity
import javax.inject.Inject

class PersonResponseMapper @Inject constructor() {

    fun map(response: PersonResponse) = PersonEntity(
        id = response.id,
        name = response.name,
        image = response.profilePath
    )
}