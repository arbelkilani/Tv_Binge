package com.arbelkilani.binge.tv.feature.details.data.repositories

import com.arbelkilani.binge.tv.common.source.remote.ApiService
import com.arbelkilani.binge.tv.feature.details.data.mapper.*
import com.arbelkilani.binge.tv.feature.details.domain.repositories.TvDetailsRepository
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class TvDetailsRepositoryImpl @Inject constructor(
    private val service: ApiService
) : TvDetailsRepository {

    private val country = Locale.getDefault().country

    @Inject
    lateinit var tvDetailsResponseMapper: TvDetailsResponseMapper

    @Inject
    lateinit var keywordsResponseMapper: KeywordsResponseMapper

    @Inject
    lateinit var castResponseMapper: CastResponseMapper

    @Inject
    lateinit var externalIdResponseMapper: ExternalIdResponseMapper

    @Inject
    lateinit var contentRatingsResponseMapper: ContentRatingsResponseMapper

    override suspend fun getTvDetails(id: Int) = flow {
        emit(tvDetailsResponseMapper.map(service.getTvDetails(id, "videos,images")))
    }

    override suspend fun getKeywords(id: Int) = flow {
        emit(service.getKeywords(id).results.map {
            keywordsResponseMapper.map(it)
        })
    }

    override suspend fun getCasts(id: Int) = flow {
        emit(service.getCredits(id).cast.map {
            castResponseMapper.map(it)
        })
    }

    override suspend fun getExternalId(id: Int) = flow {
        emit(externalIdResponseMapper.map(service.getExternalId(id)))
    }

    override suspend fun getContentRatings(id: Int) = flow {
        emit(service.getContentRatings(id).results.firstOrNull {
            it.isoCode == country
        }?.let { contentRatingsResponseMapper.map(it) })
    }
}