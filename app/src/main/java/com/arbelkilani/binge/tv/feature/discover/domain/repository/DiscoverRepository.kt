package com.arbelkilani.binge.tv.feature.discover.domain.repository

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {
    suspend fun getTrending(): Flow<List<TvEntity>>
    suspend fun getAiringToday(): Flow<PagingData<TvEntity>>
    suspend fun discover(): Flow<PagingData<TvEntity>>
    suspend fun getFavoriteProviders(): Flow<List<WatchProviderEntity>?>
}