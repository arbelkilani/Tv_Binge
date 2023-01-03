package com.arbelkilani.binge.tv.feature.walkthrough.domain.repository

import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import kotlinx.coroutines.flow.Flow

interface ResourcesRepository {
    suspend fun execute()
    suspend fun getBackdrop(): String?
    suspend fun getPoster(): String?
    suspend fun getFavoriteProviders(): Flow<List<WatchProviderEntity>?>
}