package com.arbelkilani.binge.tv.feature.discover.domain.repository

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.common.domain.entity.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.discover.domain.entity.PersonEntity
import com.arbelkilani.binge.tv.feature.discover.domain.entity.TvEntity
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {
    suspend fun getTrending(): Flow<PagingData<TvEntity>>
    suspend fun getTrendingPerson(): Flow<PagingData<PersonEntity>>
    suspend fun getFavoriteProviders(): Flow<List<WatchProviderEntity>?>
    suspend fun getFavoriteGenres(): Flow<List<GenreEntity>?>
    suspend fun getStartingThisMonth(): Flow<PagingData<TvEntity>>
    suspend fun getBasedOnProviders(): Flow<PagingData<TvEntity>>
    suspend fun getFree(): Flow<PagingData<TvEntity>>
    suspend fun getBasedOnGenres(): Flow<PagingData<TvEntity>>
    suspend fun getUpcoming(): Flow<PagingData<TvEntity>>
    suspend fun getTalkShows(): Flow<PagingData<TvEntity>>
    suspend fun getDocumentaries(): Flow<PagingData<TvEntity>>
}