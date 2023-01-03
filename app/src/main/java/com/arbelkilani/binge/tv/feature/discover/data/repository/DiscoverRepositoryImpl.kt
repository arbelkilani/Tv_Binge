package com.arbelkilani.binge.tv.feature.discover.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.source.remote.ApiService
import com.arbelkilani.binge.tv.common.source.remote.pagingsource.AiringTodayPagingSource
import com.arbelkilani.binge.tv.common.source.remote.pagingsource.TvEntityPagingSource
import com.arbelkilani.binge.tv.feature.discover.data.mapper.TvMapper
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(
    private val service: ApiService
) : DiscoverRepository {

    @Inject
    lateinit var mapper: TvMapper

    override suspend fun getTrending(): Flow<List<TvEntity>> {
        return flow {
            emit(service.getTrending(TRENDING_MEDIA_TYPE, TRENDING_TIME_WINDOW).results.map {
                mapper.map(it)
            })
        }
    }

    override suspend fun getAiringToday(): Flow<PagingData<TvEntity>> {
        return Pager(
            config = PagingConfig(OFFSET),
            pagingSourceFactory = { AiringTodayPagingSource(service, mapper) }
        ).flow
    }

    override suspend fun discover(): Flow<PagingData<TvEntity>> {
        return Pager(
            config = PagingConfig(OFFSET),
            pagingSourceFactory = { TvEntityPagingSource(service, mapper) }
        ).flow
    }

    companion object {
        private const val TRENDING_MEDIA_TYPE = "tv"
        private const val TRENDING_TIME_WINDOW = "week" // Or "day"
        private const val OFFSET = 20
    }
}