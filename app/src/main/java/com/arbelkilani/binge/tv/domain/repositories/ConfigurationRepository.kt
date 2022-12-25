package com.arbelkilani.binge.tv.domain.repositories

import kotlinx.coroutines.flow.Flow

interface ConfigurationRepository {
    suspend fun isFirstRun(): Flow<Boolean>
    suspend fun setNoFirstRun()
}