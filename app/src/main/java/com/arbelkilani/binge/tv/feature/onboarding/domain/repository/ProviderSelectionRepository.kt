package com.arbelkilani.binge.tv.feature.onboarding.domain.repository

import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import kotlinx.coroutines.flow.Flow

interface ProviderSelectionRepository {

    suspend fun getProviders(): Flow<List<WatchProviderEntity>>

    suspend fun updateProviderState(provider: WatchProviderEntity)
}