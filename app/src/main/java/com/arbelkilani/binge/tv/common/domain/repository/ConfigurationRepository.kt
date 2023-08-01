package com.arbelkilani.binge.tv.common.domain.repository

import kotlinx.coroutines.flow.Flow

interface ConfigurationRepository {
    suspend fun isFirstRun(): Flow<Boolean>
    suspend fun setNoFirstRun()
}