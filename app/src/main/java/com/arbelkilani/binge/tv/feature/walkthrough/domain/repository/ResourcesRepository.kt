package com.arbelkilani.binge.tv.feature.walkthrough.domain.repository

import com.arbelkilani.binge.tv.common.data.enum.ImageSize
import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.common.domain.entity.WatchProviderEntity
import kotlinx.coroutines.flow.Flow

interface ResourcesRepository {
    suspend fun execute()
    suspend fun getFavoriteProviders(): Flow<List<WatchProviderEntity>?>
    suspend fun getFavoriteGenres(): Flow<List<GenreEntity>?>
    suspend fun getBaseImage(size: ImageSize): String
}