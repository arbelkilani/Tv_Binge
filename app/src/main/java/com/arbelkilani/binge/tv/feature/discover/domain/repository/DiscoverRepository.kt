package com.arbelkilani.binge.tv.feature.discover.domain.repository

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {
    suspend fun getTrending(): Flow<List<TvEntity>>
    suspend fun getAiringToday(): Flow<PagingData<TvEntity>>
}