package com.arbelkilani.binge.tv.feature.discover.data.mapper

import com.arbelkilani.binge.tv.feature.discover.data.entities.PersonResponse
import com.arbelkilani.binge.tv.feature.discover.domain.entities.PersonEntity
import javax.inject.Inject

class PersonResponseMapper @Inject constructor() {

    fun map(response: PersonResponse) = PersonEntity(
        id = response.id,
        name = response.name,
        image = response.profilePath
    )
}