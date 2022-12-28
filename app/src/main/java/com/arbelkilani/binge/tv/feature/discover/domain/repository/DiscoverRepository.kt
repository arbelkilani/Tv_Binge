package com.arbelkilani.binge.tv.feature.discover.domain.repository

import com.arbelkilani.binge.tv.feature.discover.data.entities.TrendingResponse
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {
    suspend fun getTrending(): Flow<List<TrendingResponse>>
}