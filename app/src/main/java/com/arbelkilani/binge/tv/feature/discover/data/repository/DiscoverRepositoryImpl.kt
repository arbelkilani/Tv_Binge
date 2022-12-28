package com.arbelkilani.binge.tv.feature.discover.data.repository

import com.arbelkilani.binge.tv.common.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.common.source.remote.ApiService
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val database: AppDatabase
) : DiscoverRepository {

    override suspend fun getTrending() = flow {
        emit(service.getTrending(TRENDING_MEDIA_TYPE, TRENDING_TIME_WINDOW).results)
    }

    companion object {
        private const val TRENDING_MEDIA_TYPE = "tv"
        private const val TRENDING_TIME_WINDOW = "week" // "day"
    }
}