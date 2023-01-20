package com.arbelkilani.binge.tv.common.domain.repository

import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    suspend fun getGenres(): Flow<List<GenreEntity>>
    suspend fun getGenreById(id: String): GenreEntity?
    suspend fun getGenresByIds(ids: List<String>): List<GenreEntity>
    suspend fun updateGenreState(id: String, isFavorite: Boolean)
    suspend fun saveGenre(genreEntity: GenreEntity)
    suspend fun saveGenres(list: List<GenreEntity>)
}