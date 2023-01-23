package com.arbelkilani.binge.tv.common.domain.repository

import com.arbelkilani.binge.tv.common.domain.entity.ProviderEntity

interface ProviderRepository {
    suspend fun getProvidersById(id: Int): List<ProviderEntity>
}