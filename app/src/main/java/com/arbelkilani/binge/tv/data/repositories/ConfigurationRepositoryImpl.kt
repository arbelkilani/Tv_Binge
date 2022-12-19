package com.arbelkilani.binge.tv.data.repositories

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import com.arbelkilani.binge.tv.data.source.local.prefsstore.PrefsStore
import com.arbelkilani.binge.tv.data.source.remote.ApiService
import com.arbelkilani.binge.tv.domain.repositories.ConfigurationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ConfigurationRepository {

    @Inject
    lateinit var prefsStore: PrefsStore

    @Inject
    lateinit var application: Application

    override fun isFirstRun(): Flow<Boolean> {
        application.packageManager.getInstalledApplications(PackageManager.GET_META_DATA).map {
            Log.i("TAG**", "it = ${it.packageName}")
        }
        return prefsStore.isFirstRun()
    }

    override suspend fun execute() {
        apiService.getGenres().list.map {
            Log.i("TAG**", "Genre : $it")
        }
    }
}