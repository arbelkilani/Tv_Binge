package com.arbelkilani.binge.tv.feature.home.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.data.mapper.ProviderResponseMapper
import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.common.domain.entity.ProviderEntity
import com.arbelkilani.binge.tv.common.domain.entity.WatchProviderEntity
import com.arbelkilani.binge.tv.common.domain.repository.ResourcesRepository
import com.arbelkilani.binge.tv.common.source.remote.ApiService
import com.arbelkilani.binge.tv.common.source.remote.pagingsource.DiscoverPagingSource
import com.arbelkilani.binge.tv.common.source.remote.pagingsource.TrendingPagingSource
import com.arbelkilani.binge.tv.common.source.remote.pagingsource.TrendingPersonPagingSource
import com.arbelkilani.binge.tv.feature.home.data.mapper.PersonResponseMapper
import com.arbelkilani.binge.tv.feature.home.data.mapper.TvResponseMapper
import com.arbelkilani.binge.tv.feature.home.data.request.DiscoverQuery
import com.arbelkilani.binge.tv.feature.home.domain.entity.PersonEntity
import com.arbelkilani.binge.tv.feature.home.domain.entity.TvEntity
import com.arbelkilani.binge.tv.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.single
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val service: ApiService
) : HomeRepository {

    private val country = Locale.getDefault().country
    private val timezone = TimeZone.getDefault().id
    private val today = LocalDate.now().toString()

    @Inject
    lateinit var tvResponseMapper: TvResponseMapper

    @Inject
    lateinit var personResponseMapper: PersonResponseMapper

    @Inject
    lateinit var providerResponseMapper: ProviderResponseMapper

    @Inject
    lateinit var resourceRepository: ResourcesRepository

    override suspend fun getTrendingPerson(): Flow<PagingData<PersonEntity>> {
        return Pager(
            config = PagingConfig(OFFSET),
            pagingSourceFactory = { TrendingPersonPagingSource(service, personResponseMapper) }
        ).flow
    }

    override suspend fun getTrending(): Flow<PagingData<TvEntity>> {
        return Pager(
            config = PagingConfig(OFFSET),
            pagingSourceFactory = { TrendingPagingSource(service, tvResponseMapper) }
        ).flow
    }

    override suspend fun getUpcoming(): Flow<PagingData<TvEntity>> {
        val discoverQuery = DiscoverQuery.Builder()
            .timezone(timezone)
            .airDateGte(today)
            .type(DiscoverQuery.Type.SCRIPTED)
            .watchRegion(country).build()

        return flowPagingAdapter(discoverQuery)
    }

    override suspend fun getStartingThisMonth(): Flow<PagingData<TvEntity>> {
        val discoverQuery = DiscoverQuery.Builder()
            .sortBy(DiscoverQuery.SortBy.FIRST_AIR_DATE_ASC)
            .timezone(timezone)
            .firstAirDateGte("2023-01-01")
            .firstAirDateLte("2023-01-31")
            .watchRegion(country).build()

        return flowPagingAdapter(discoverQuery)

    }

    override suspend fun getBasedOnProviders(): Flow<PagingData<TvEntity>> {
        val discoverQuery = DiscoverQuery.Builder()
            .timezone(timezone)
            .watchProviders(getProvidersString())
            .watchRegion(country).build()

        return flowPagingAdapter(discoverQuery)
    }

    override suspend fun getFree(): Flow<PagingData<TvEntity>> {
        val discoverQuery = DiscoverQuery.Builder()
            .timezone(timezone)
            .monetizationType(DiscoverQuery.MonetizationType.FREE)
            .watchRegion(country).build()

        return Pager(
            config = PagingConfig(OFFSET),
            pagingSourceFactory = {
                DiscoverPagingSource(service, tvResponseMapper, discoverQuery)
            }).flow
    }

    override suspend fun getBasedOnGenres(): Flow<PagingData<TvEntity>> {
        val discoverQuery = DiscoverQuery.Builder()
            .timezone(timezone)
            .genres(getGenresString())
            .watchRegion(country).build()

        return flowPagingAdapter(discoverQuery)
    }

    override suspend fun getTalkShows(): Flow<PagingData<TvEntity>> {
        val discoverQuery = DiscoverQuery.Builder()
            .timezone(timezone)
            .type(DiscoverQuery.Type.TALK_SHOW)
            .watchRegion(country).build()

        return flowPagingAdapter(discoverQuery)
    }

    override suspend fun getDocumentaries(): Flow<PagingData<TvEntity>> {
        val discoverQuery = DiscoverQuery.Builder()
            .timezone(timezone)
            .type(DiscoverQuery.Type.DOCUMENTARY)
            .watchRegion(country).build()

        return flowPagingAdapter(discoverQuery)
    }

    private fun flowPagingAdapter(discoverQuery: HashMap<String, String>): Flow<PagingData<TvEntity>> {
        return Pager(
            config = PagingConfig(OFFSET),
            pagingSourceFactory = {
                DiscoverPagingSource(service, tvResponseMapper, discoverQuery)
            }).flow
    }

    override suspend fun getFavoriteProviders(): Flow<List<WatchProviderEntity>?> {
        return resourceRepository.getFavoriteProviders()
    }

    override suspend fun getFavoriteGenres(): Flow<List<GenreEntity>?> {
        return resourceRepository.getFavoriteGenres()
    }

    private suspend fun getProvidersString(): String? {
        return resourceRepository.getFavoriteProviders().single()
            ?.map { it.id }
            ?.joinToString(separator = "|")
    }

    private suspend fun getGenresString(): String? {
        return resourceRepository.getFavoriteGenres().single()
            ?.map { it.id }
            ?.joinToString(separator = "|")
    }

    private suspend fun getTvProviders(id: Int): List<ProviderEntity>? {
        val result = service.getTvWatchProviders(id).result.filterKeys { key ->
            key == country
        }.map { map ->
            map.value
        }.firstOrNull()
        return result?.let { providerResponseMapper.map(it) }
    }


    companion object {
        private const val OFFSET = 20
    }
}