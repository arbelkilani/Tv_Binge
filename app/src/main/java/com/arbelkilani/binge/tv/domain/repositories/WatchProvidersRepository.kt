package com.arbelkilani.binge.tv.domain.repositories

import com.arbelkilani.binge.tv.domain.entities.WatchProviderEntity
import kotlinx.coroutines.flow.Flow

interface WatchProvidersRepository {
    suspend fun getProviders(): Flow<List<WatchProviderEntity>>
    fun saveSelectedWatchProviders(providersId: MutableList<WatchProviderEntity>)
}