package com.arbelkilani.binge.tv.feature.discover.data.repository

import com.arbelkilani.binge.tv.common.source.remote.ApiService
import com.arbelkilani.binge.tv.feature.discover.data.mapper.TrendingMapper
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TrendingEntity
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(
    private val service: ApiService
) : DiscoverRepository {

    @Inject
    lateinit var mapper: TrendingMapper

    override suspend fun getTrending(): Flow<List<TrendingEntity>> {
        return flow {
            emit(service.getTrending(TRENDING_MEDIA_TYPE, TRENDING_TIME_WINDOW).results.map {
                mapper.map(it)
            })
        }
    }

    companion object {
        private const val TRENDING_MEDIA_TYPE = "tv"
        private const val TRENDING_TIME_WINDOW = "week" // Or "day"
    }
}