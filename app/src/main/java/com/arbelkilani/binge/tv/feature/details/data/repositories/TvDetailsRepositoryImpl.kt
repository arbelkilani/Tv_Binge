package com.arbelkilani.binge.tv.feature.details.data.repositories

import com.arbelkilani.binge.tv.common.source.remote.ApiService
import com.arbelkilani.binge.tv.feature.details.data.mapper.CastResponseMapper
import com.arbelkilani.binge.tv.feature.details.data.mapper.KeywordsResponseMapper
import com.arbelkilani.binge.tv.feature.details.data.mapper.TvDetailsResponseMapper
import com.arbelkilani.binge.tv.feature.details.domain.repositories.TvDetailsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TvDetailsRepositoryImpl @Inject constructor(
    private val service: ApiService
) : TvDetailsRepository {

    @Inject
    lateinit var tvDetailsResponseMapper: TvDetailsResponseMapper

    @Inject
    lateinit var keywordsResponseMapper: KeywordsResponseMapper

    @Inject
    lateinit var castResponseMapper: CastResponseMapper

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
}