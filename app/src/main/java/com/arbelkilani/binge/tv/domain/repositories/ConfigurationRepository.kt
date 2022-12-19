package com.arbelkilani.binge.tv.domain.repositories

import kotlinx.coroutines.flow.Flow

interface ConfigurationRepository {
    fun isFirstRun(): Flow<Boolean>
    suspend fun execute()
    suspend fun saveConfiguration()
}