package com.arbelkilani.binge.tv.feature.discover.domain.repository

import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {
    suspend fun getGenres(): Flow<List<GenreEntity>>
}