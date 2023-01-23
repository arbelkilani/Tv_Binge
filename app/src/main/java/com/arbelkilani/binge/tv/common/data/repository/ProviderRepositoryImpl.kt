package com.arbelkilani.binge.tv.common.data.repository

import com.arbelkilani.binge.tv.common.data.mapper.ProviderResponseMapper
import com.arbelkilani.binge.tv.common.domain.entity.ProviderEntity
import com.arbelkilani.binge.tv.common.domain.repository.ProviderRepository
import com.arbelkilani.binge.tv.common.source.remote.ApiService
import java.util.*
import javax.inject.Inject

class ProviderRepositoryImpl @Inject constructor(
    private val service: ApiService
) : ProviderRepository {

    private val country = Locale.getDefault().country
    
    @Inject
    lateinit var providerResponseMapper: ProviderResponseMapper

    override suspend fun getProvidersById(id: Int): List<ProviderEntity> {
        val result = service.getTvWatchProviders(id).result.filterKeys { key ->
            key == country
        }.map { map ->
            map.value
        }.firstOrNull()
        return result?.let { providerResponseMapper.map(it) } ?: emptyList()
    }
}