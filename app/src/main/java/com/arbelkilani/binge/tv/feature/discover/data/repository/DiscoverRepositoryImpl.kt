package com.arbelkilani.binge.tv.feature.discover.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.data.mapper.GenreResponseMapper
import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.common.presentation.model.Genre
import com.arbelkilani.binge.tv.common.presentation.model.Provider
import com.arbelkilani.binge.tv.common.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.common.source.remote.ApiService
import com.arbelkilani.binge.tv.common.source.remote.pagingsource.DiscoverPagingSource
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import com.arbelkilani.binge.tv.feature.home.data.mapper.TvResponseMapper
import com.arbelkilani.binge.tv.feature.home.data.request.DiscoverQuery
import com.arbelkilani.binge.tv.feature.home.domain.entity.TvEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(
    private val service: ApiService,
    database: AppDatabase
) : DiscoverRepository {

    @Inject
    lateinit var genreResponseMapper: GenreResponseMapper

    @Inject
    lateinit var tvResponseMapper: TvResponseMapper

    private val resourcesDao = database.resourcesDao()
    private val country = Locale.getDefault().country
    private val timezone = TimeZone.getDefault().id

    override suspend fun getGenres(): Flow<List<GenreEntity>> {
        val local = resourcesDao.getGenres()
        return if (local.isNullOrEmpty()) {
            flow {
                emit(service.getGenres().list.map {
                    genreResponseMapper.map(it, false)
                })
            }
        } else {
            flow { emit(local) }
        }
    }

    override suspend fun discover(): Flow<PagingData<TvEntity>> {
        val discoverQuery = DiscoverQuery.Builder()
            .timezone(timezone)
            .watchRegion(country).build()

        return Pager(
            config = PagingConfig(OFFSET),
            pagingSourceFactory = {
                DiscoverPagingSource(service, tvResponseMapper, discoverQuery)
            }).flow
    }

    override suspend fun filterShows(
        genres: MutableList<Genre>,
        providers: MutableList<Provider>
    ): Flow<PagingData<TvEntity>> {
        val discoverQuery = DiscoverQuery.Builder()
            .timezone(timezone)
            .genres(genres.joinToString(separator = ",") { it.id.toString() })
            .watchProviders(providers.joinToString(separator = ",") { it.id.toString() })
            .watchRegion(country).build()

        return Pager(
            config = PagingConfig(OFFSET),
            pagingSourceFactory = {
                DiscoverPagingSource(service, tvResponseMapper, discoverQuery)
            }).flow
    }

    companion object {
        private const val OFFSET = 20
    }
}