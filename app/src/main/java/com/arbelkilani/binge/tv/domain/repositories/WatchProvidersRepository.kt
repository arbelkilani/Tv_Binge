package com.arbelkilani.binge.tv.domain.repositories

import com.arbelkilani.binge.tv.domain.entities.WatchProviderEntity
import kotlinx.coroutines.flow.Flow

interface WatchProvidersRepository {
    fun getProviders(): Flow<List<WatchProviderEntity>>
}