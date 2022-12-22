package com.arbelkilani.binge.tv.common.data.mapper

import com.arbelkilani.binge.tv.common.data.model.Genre
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import javax.inject.Inject

class GenreMapper @Inject constructor() {

    fun map(genre: Genre, isFavorite: Boolean) = GenreEntity(
        id = genre.id,
        name = genre.name,
        isFavorite = isFavorite
    )
}