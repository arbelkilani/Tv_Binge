package com.arbelkilani.binge.tv.domain.repositories

import kotlinx.coroutines.flow.Flow

interface ConfigurationRepository {
    suspend fun saveConfiguration()
    suspend fun isFirstRun(): Flow<Boolean>
    suspend fun toggleIsFirstRunState()
}