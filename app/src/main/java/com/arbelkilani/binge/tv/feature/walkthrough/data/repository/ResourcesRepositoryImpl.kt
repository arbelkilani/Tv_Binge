package com.arbelkilani.binge.tv.feature.walkthrough.data.repository

import android.app.Application
import com.arbelkilani.binge.tv.common.data.mapper.ApiConfigurationMapper
import com.arbelkilani.binge.tv.common.data.mapper.CertificationMapper
import com.arbelkilani.binge.tv.common.data.mapper.GenreMapper
import com.arbelkilani.binge.tv.common.data.mapper.WatchProviderMapper
import com.arbelkilani.binge.tv.common.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.common.source.remote.ApiService
import com.arbelkilani.binge.tv.feature.walkthrough.domain.repository.ResourcesRepository
import java.util.*
import javax.inject.Inject

class ResourcesRepositoryImpl @Inject constructor(
    private val service: ApiService, database: AppDatabase
) : ResourcesRepository {

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var watchProviderMapper: WatchProviderMapper

    @Inject
    lateinit var apiConfigurationMapper: ApiConfigurationMapper

    @Inject
    lateinit var certificationMapper: CertificationMapper

    @Inject
    lateinit var genreMapper: GenreMapper

    private val resourcesDao = database.resourcesDao()
    private val country = Locale.getDefault().country

    override suspend fun execute() {
        saveWatchProviders()
        saveApiConfiguration()
        saveCertifications()
        saveGenres()
    }

    private suspend fun saveWatchProviders() {
        if (resourcesDao.getWatchLocalProviders().isNullOrEmpty()) {
            service.getProviders(country).providers
                .map { provider ->
                    resourcesDao.saveWatchProvider(
                        watchProviderMapper.map(
                            provider,
                            getInstalledPackages().contains(provider.providerName)
                        )
                    )
                }
        }
    }

    private suspend fun saveApiConfiguration() {
        if (resourcesDao.getApiConfiguration() == null) {
            resourcesDao.saveApiConfiguration(
                apiConfigurationMapper.map(service.getConfiguration())
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
                resourcesDao.saveGenre(genreMapper.map(it, false))
            }
        }
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

    override suspend fun getBackdrop(): String? {
        return resourcesDao.getApiConfiguration()?.backdrop?.large
    }

    override suspend fun getPoster(): String? {
        return resourcesDao.getApiConfiguration()?.poster?.large
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