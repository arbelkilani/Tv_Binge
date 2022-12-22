package com.arbelkilani.binge.tv.feature.walkthrough.data.repository

import com.arbelkilani.binge.tv.common.data.mapper.ApiConfigurationMapper
import com.arbelkilani.binge.tv.common.data.mapper.CertificationMapper
import com.arbelkilani.binge.tv.common.data.mapper.GenreMapper
import com.arbelkilani.binge.tv.common.data.mapper.WatchProviderMapper
import com.arbelkilani.binge.tv.data.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.data.source.remote.ApiService
import com.arbelkilani.binge.tv.feature.walkthrough.domain.repository.ResourcesRepository
import java.util.*
import javax.inject.Inject

class ResourcesRepositoryImpl @Inject constructor(
    private val service: ApiService,
    database: AppDatabase
) : ResourcesRepository {

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
            service.getProviders(country).providers.map {
                resourcesDao.saveWatchProvider(watchProviderMapper.map(it, false))
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
}