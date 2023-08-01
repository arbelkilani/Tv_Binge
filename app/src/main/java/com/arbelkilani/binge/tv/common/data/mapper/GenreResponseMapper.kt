package com.arbelkilani.binge.tv.common.data.mapper

import com.arbelkilani.binge.tv.common.data.response.GenreResponse
import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import javax.inject.Inject

class GenreResponseMapper @Inject constructor() {

    fun map(response: GenreResponse, isFavorite: Boolean) = GenreEntity(
        id = response.id,
        name = response.name,
        isFavorite = isFavorite
    )
}