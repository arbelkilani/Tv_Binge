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
    private val database: AppDatabase
) : ConfigurationRepository {

    @Inject
    lateinit var prefsStore: PrefsStore

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var configurationMapper: ConfigurationMapper

    override fun isFirstRun(): Flow<Boolean> {
        return prefsStore.isFirstRun()
    }

    override suspend fun execute() {
        apiService.getGenres().list.map {
            Log.i("TAG**", "Genre : $it")
        }
    }

    override suspend fun saveConfiguration() {
        try {
            database.configurationDao().insert(
                configurationMapper.map(apiService.getConfiguration())
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}