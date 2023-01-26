package com.arbelkilani.binge.tv.common.domain.repository

import com.arbelkilani.binge.tv.common.domain.entity.ProviderEntity
import kotlinx.coroutines.flow.Flow

interface ProviderRepository {
    suspend fun getProvidersById(id: Int): List<ProviderEntity>
    suspend fun getProviders(): Flow<List<ProviderEntity>>
}