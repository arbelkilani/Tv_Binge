package com.arbelkilani.binge.tv.feature.discover.domain.repository

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {
    suspend fun getTrending(): Flow<PagingData<TvEntity>>
    suspend fun getAiringToday(): Flow<PagingData<TvEntity>>
    suspend fun discover(): Flow<PagingData<TvEntity>>
    suspend fun getFavoriteProviders(): Flow<List<WatchProviderEntity>?>
    suspend fun getFavoriteGenres(): Flow<List<GenreEntity>?>
    suspend fun getStartingThisMonth(): Flow<PagingData<TvEntity>>
    suspend fun getBasedOnProviders(): Flow<PagingData<TvEntity>>
    suspend fun getFree(): Flow<PagingData<TvEntity>>
}