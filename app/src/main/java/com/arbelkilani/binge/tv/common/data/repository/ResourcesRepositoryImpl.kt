package com.arbelkilani.binge.tv.common.data.repository

import android.app.Application
import com.arbelkilani.binge.tv.common.data.enum.ImageSize
import com.arbelkilani.binge.tv.common.data.mapper.CertificationMapper
import com.arbelkilani.binge.tv.common.data.mapper.ConfigurationResponseMapper
import com.arbelkilani.binge.tv.common.data.mapper.GenreResponseMapper
import com.arbelkilani.binge.tv.common.data.mapper.WatchProviderResponseMapper
import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.common.domain.entity.WatchProviderEntity
import com.arbelkilani.binge.tv.common.domain.repository.ResourcesRepository
import com.arbelkilani.binge.tv.common.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.common.source.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class ResourcesRepositoryImpl @Inject constructor(
    private val service: ApiService, database: AppDatabase
) : ResourcesRepository {

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var watchProviderResponseMapper: WatchProviderResponseMapper

    @Inject
    lateinit var configurationResponseMapper: ConfigurationResponseMapper

    @Inject
    lateinit var certificationMapper: CertificationMapper

    @Inject
    lateinit var genreResponseMapper: GenreResponseMapper

    private val resourcesDao = database.resourcesDao()
    private val country = Locale.getDefault().country

    override suspend fun execute() {
        saveWatchProviders()
        saveApiConfiguration()
        saveCertifications()
        saveGenres()
    }

    private suspend fun saveWatchProviders() {
        /*if (resourcesDao.getWatchLocalProviders().isNullOrEmpty()) {
            service.getProviders(country).results.map { provider ->
                resourcesDao.saveWatchProvider(
                    watchProviderResponseMapper.map(
                        provider, getInstalledPackages().contains(provider.providerName)
                    )
                )
            }
        }*/
    }

    private suspend fun saveApiConfiguration() {
        if (resourcesDao.getConfiguration() == null) {
            resourcesDao.saveConfiguration(
                configurationResponseMapper.map(service.getConfiguration())
            )
        }
    }

    private suspend fun saveCertifications() {
        if (resourcesDao.getCertifications().isNullOrEmpty()) {
            service.getCertifications().certifications[country]?.map {
                resourcesDao.saveCertification(certificationMapper.map(it))
            }
        }
    }

    private suspend fun saveGenres() {
        if (resourcesDao.getGenres().isNullOrEmpty()) {
            service.getGenres().list.map {
                resourcesDao.saveGenre(genreResponseMapper.map(it, false))
            }
        }
    }

    override suspend fun getFavoriteProviders(): Flow<List<WatchProviderEntity>?> {
        return flow {
            emit(resourcesDao.getFavoriteProviders()?.sortedByDescending {
                it.priority
            })
        }
    }

    override suspend fun getFavoriteGenres(): Flow<List<GenreEntity>?> {
        return flow { emit(resourcesDao.getFavoriteGenres()) }
    }

    // TODO: 24-12-2022
    // replace the known packages map and fill it later
    // with significant data.
    private fun getInstalledPackages(): List<String> {
        val packageManager = application.packageManager
        return knownPackages.filter {
            packageManager.getLaunchIntentForPackage(it.key) != null
        }.map {
            it.value
        }
    }

    override suspend fun getBaseImage(size: ImageSize): String {
        return resourcesDao.getConfiguration()?.let { local ->
            local.url + size.size
        } ?: run {
            configurationResponseMapper.map(service.getConfiguration()).apply {
                resourcesDao.saveConfiguration(this)
            }.url + size.size
        }
    }

    companion object {
        val knownPackages = mapOf(
            "com.google.android.videos" to "Google Play Movies",
            "com.netflix.mediaclient" to "Netflix",
            "com.crunchyroll.crunchyroid" to "Crunchyroll",
            "com.disney.disneyplus" to "Disney Plus",
            "com.amazon.avod.thirdpartyclient" to "Amazon Prime Video"
        )
    }
}