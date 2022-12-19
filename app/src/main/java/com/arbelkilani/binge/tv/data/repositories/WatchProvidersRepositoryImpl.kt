package com.arbelkilani.binge.tv.data.repositories

import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import com.arbelkilani.binge.tv.data.mapper.WatchProviderMapper
import com.arbelkilani.binge.tv.data.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.data.source.remote.ApiService
import com.arbelkilani.binge.tv.domain.entities.WatchProviderEntity
import com.arbelkilani.binge.tv.domain.repositories.WatchProvidersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class WatchProvidersRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val database: AppDatabase
) : WatchProvidersRepository {

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var watchProviderMapper: WatchProviderMapper

    override fun getProviders(): Flow<List<WatchProviderEntity>> {
        val installedPackages = getInstalledPackages()
        return flow {
            emit(
                service.getProviders(Locale.getDefault().country).providers.map { provider ->
                    installedPackages.map { installed ->
                        watchProviderMapper.map(
                            provider,
                            installed.contains(provider.providerName, true)
                        )
                    }.distinct()
                }.flatten()
            )
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getInstalledPackages(): List<String> {
        val packageManager = application.packageManager
        return packageManager.getInstalledPackages(PackageManager.GET_META_DATA).filter {
            packageManager.getLaunchIntentForPackage(it.packageName) != null
        }.map {
            it.packageName
        }
    }
}