package com.arbelkilani.binge.tv.data.repositories

import com.arbelkilani.binge.tv.data.source.local.prefsstore.PrefsStore
import com.arbelkilani.binge.tv.data.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.domain.repositories.ConfigurationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : ConfigurationRepository {

    @Inject
    lateinit var prefsStore: PrefsStore


    override suspend fun isFirstRun(): Flow<Boolean> {
        return prefsStore.isFirstRun()
    }

    override suspend fun toggleIsFirstRunState() {
        prefsStore.toggleIsFirstRunState()
    }

    override suspend fun saveConfiguration() {
        val dao = appDatabase.resourcesDao()
    }
}