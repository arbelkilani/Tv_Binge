package com.arbelkilani.binge.tv.common.data.repository

import com.arbelkilani.binge.tv.common.data.mapper.ProviderResponseMapper
import com.arbelkilani.binge.tv.common.data.mapper.WatchProviderResponseMapper
import com.arbelkilani.binge.tv.common.domain.entity.ProviderEntity
import com.arbelkilani.binge.tv.common.domain.repository.ProviderRepository
import com.arbelkilani.binge.tv.common.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.common.source.remote.ApiService
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class ProviderRepositoryImpl @Inject constructor(
    private val service: ApiService,
    database: AppDatabase
) : ProviderRepository {

    @Inject
    lateinit var providerResponseMapper: ProviderResponseMapper

    @Inject
    lateinit var watchProviderResponseMapper: WatchProviderResponseMapper

    private val country = Locale.getDefault().country
    private val resourcesDao = database.resourcesDao()

    override suspend fun getProvidersById(id: Int): List<ProviderEntity> {
        val result = service.getTvWatchProviders(id).result.filterKeys { key ->
            key == country
        }.map { map ->
            map.value
        }.firstOrNull()
        return result?.let { providerResponseMapper.map(it) } ?: emptyList()
    }

    override suspend fun getProviders() = flow {
        val local = resourcesDao.getProviders()
        if (local.isNullOrEmpty()) {
            emit(service.getProviders(country).results.map { response ->
                watchProviderResponseMapper.map(response, isFavorite = false)
            })
        } else emit(local)
    }
}