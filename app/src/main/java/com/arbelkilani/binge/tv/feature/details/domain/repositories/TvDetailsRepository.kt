package com.arbelkilani.binge.tv.feature.details.domain.repositories

import com.arbelkilani.binge.tv.feature.details.domain.entities.TvDetailsEntity
import kotlinx.coroutines.flow.Flow

interface TvDetailsRepository {
    suspend fun getTvDetails(id: Int): Flow<TvDetailsEntity>
}