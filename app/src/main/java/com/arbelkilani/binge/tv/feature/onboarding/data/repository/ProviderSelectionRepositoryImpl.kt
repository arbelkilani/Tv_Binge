package com.arbelkilani.binge.tv.feature.onboarding.data.repository

import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.data.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.feature.onboarding.domain.repository.ProviderSelectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProviderSelectionRepositoryImpl @Inject constructor(
    database: AppDatabase
) : ProviderSelectionRepository {

    private val resourcesDao = database.resourcesDao()

    override suspend fun getProviders(): Flow<List<WatchProviderEntity>> {
        val base = resourcesDao.getApiConfiguration()?.logo?.large
        return flow {
            resourcesDao.getWatchLocalProviders()?.let { list ->
                emit(list.map { entity ->
                    entity.copy(
                        logo = base + entity.logo
                    )
                })
            }
        }
    }

    override suspend fun updateProviderState(provider: WatchProviderEntity) {
        resourcesDao.updateProviderState(provider.id, provider.isFavorite)
    }
}