package com.arbelkilani.binge.tv.feature.search.domain.repository

import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getGenres(): Flow<List<GenreEntity>>
}