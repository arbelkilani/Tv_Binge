package com.arbelkilani.binge.tv.feature.onboarding.data.repository

import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.common.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.feature.onboarding.domain.repository.OnBoardingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(
    database: AppDatabase
) : OnBoardingRepository {

    private val resourcesDao = database.resourcesDao()

    override suspend fun getProviders(): Flow<List<WatchProviderEntity>> {
        val base = resourcesDao.getApiConfiguration()?.logo?.medium
        return flow {
            resourcesDao.getWatchLocalProviders()?.let { list ->
                emit(list.map { entity -> entity.copy(logo = base + entity.logo) })
            }
        }
    }

    override suspend fun updateProviderState(provider: WatchProviderEntity) {
        resourcesDao.updateProviderState(provider.id, provider.isFavorite)
    }

    override suspend fun getGenres() = flow {
        resourcesDao.getGenres()?.let { emit(it) }
    }

    override suspend fun updateGenreState(genreEntity: GenreEntity) {
        resourcesDao.updateGenreState(genreEntity.id, genreEntity.isFavorite)
    }
}