package com.arbelkilani.binge.tv.common.domain.repository

import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity

interface GenreRepository {
    suspend fun getGenresByIds(ids: List<String>): List<GenreEntity>
    suspend fun getGenres(): List<GenreEntity>
}