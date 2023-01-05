package com.arbelkilani.binge.tv.feature.discover.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.common.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.common.source.remote.ApiService
import com.arbelkilani.binge.tv.common.source.remote.pagingsource.AiringTodayPagingSource
import com.arbelkilani.binge.tv.common.source.remote.pagingsource.DiscoverPagingSource
import com.arbelkilani.binge.tv.feature.discover.data.mapper.TvMapper
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import com.arbelkilani.binge.tv.feature.walkthrough.domain.repository.ResourcesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import java.util.*
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val database: AppDatabase
) : DiscoverRepository {

    private val country = Locale.getDefault().country
    private val timezone = TimeZone.getDefault().id

    @Inject
    lateinit var mapper: TvMapper

    @Inject
    lateinit var resourceRepository: ResourcesRepository

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
        val queryMap = hashMapOf(
            //"air_date.gte" to "2023-01-01",
            //"air_date.lte" to "2023-01-31",
            "watch_region" to country,
            //"with_watch_providers" to getProvidersString(),
            //"with_genres" to getGenresString(),
            "first_air_date.gte" to "2023-01-05",
            "first_air_date.lte" to "2023-01-31",
            "sort_by" to "first_air_date.asc",
            "with_original_language" to "ar|en|fr|jp"
            //"with_watch_monetization_types" to "ads,rent,buy" //flatrate, free, ads, rent, buy
        )

        return Pager(
            config = PagingConfig(OFFSET),
            pagingSourceFactory = { DiscoverPagingSource(service, mapper, queryMap) }
        ).flow
    }

    override suspend fun getFavoriteProviders(): Flow<List<WatchProviderEntity>?> {
        return resourceRepository.getFavoriteProviders()
    }

    override suspend fun getFavoriteGenres(): Flow<List<GenreEntity>?> {
        return resourceRepository.getFavoriteGenres()
    }

    /**
     *
     */
    private suspend fun getProvidersString(): String? {
        return resourceRepository.getFavoriteProviders().single()?.map { it.id }
            ?.joinToString(separator = "|")
    }

    private suspend fun getGenresString(): String? {
        return resourceRepository.getFavoriteGenres().single()?.map { it.id }
            ?.joinToString(separator = "|")
    }

    /**
     *
     */
    private suspend fun getGteAndLteDates(): Pair<String, String> {
        return Pair("", "")
    }

    companion object {
        private const val TRENDING_MEDIA_TYPE = "tv"
        private const val TRENDING_TIME_WINDOW = "week" // Or "day"
        private const val OFFSET = 20
    }
}