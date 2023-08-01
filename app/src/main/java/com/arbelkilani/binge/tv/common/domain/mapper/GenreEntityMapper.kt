package com.arbelkilani.binge.tv.common.domain.mapper

import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.common.presentation.model.Genre
import javax.inject.Inject

class GenreEntityMapper @Inject constructor() {
    fun map(entity: GenreEntity) = Genre(
        id = entity.id,
        name = entity.name,
        isFavorite = entity.isFavorite
    )
}