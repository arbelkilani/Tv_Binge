package com.arbelkilani.binge.tv.feature.walkthrough.domain.repository

import com.arbelkilani.binge.tv.common.data.enum.ImageSize
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import kotlinx.coroutines.flow.Flow

interface ResourcesRepository {
    suspend fun execute()
    suspend fun getBackdrop(): String?
    suspend fun getPoster(): String?
    suspend fun getFavoriteProviders(): Flow<List<WatchProviderEntity>?>
    suspend fun getFavoriteGenres(): Flow<List<GenreEntity>?>
    suspend fun getLogo(size: ImageSize): String
}