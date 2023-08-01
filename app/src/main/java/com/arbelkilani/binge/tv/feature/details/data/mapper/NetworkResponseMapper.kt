package com.arbelkilani.binge.tv.feature.details.data.mapper

import com.arbelkilani.binge.tv.feature.details.data.entities.NetworkResponse
import com.arbelkilani.binge.tv.feature.details.domain.entities.NetworkEntity
import javax.inject.Inject

class NetworkResponseMapper @Inject constructor() {

    fun map(response: NetworkResponse) = NetworkEntity(
        id = response.id,
        name = response.name,
        logo = response.logo
    )
}