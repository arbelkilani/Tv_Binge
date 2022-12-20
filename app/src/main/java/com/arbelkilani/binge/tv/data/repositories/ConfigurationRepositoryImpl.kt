package com.arbelkilani.binge.tv.data.repositories

import android.app.Application
import android.util.Log
import com.arbelkilani.binge.tv.data.mapper.ConfigurationMapper
import com.arbelkilani.binge.tv.data.source.local.prefsstore.PrefsStore
import com.arbelkilani.binge.tv.data.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.data.source.remote.ApiService
import com.arbelkilani.binge.tv.domain.repositories.ConfigurationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : ConfigurationRepository {

    @Inject
    lateinit var prefsStore: PrefsStore

    @Inject
    lateinit var configurationMapper: ConfigurationMapper

    override suspend fun isFirstRun(): Flow<Boolean> {
        return prefsStore.isFirstRun()
    }

    override suspend fun saveConfiguration() {
        val dao = appDatabase.configurationDao()
        val localConfiguration = dao.get()
        if (localConfiguration == null) {
            dao.insert(
                configurationMapper.map(apiService.getConfiguration())
            )
        }
    }
}