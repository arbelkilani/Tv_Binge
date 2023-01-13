package com.arbelkilani.binge.tv.feature.details.data.repositories

import com.arbelkilani.binge.tv.common.source.remote.ApiService
import com.arbelkilani.binge.tv.feature.details.data.mapper.TvDetailsResponseMapper
import com.arbelkilani.binge.tv.feature.details.domain.entities.TvDetailsEntity
import com.arbelkilani.binge.tv.feature.details.domain.repositories.TvDetailsRepository
import javax.inject.Inject

class TvDetailsRepositoryImpl @Inject constructor(
    private val service: ApiService
) : TvDetailsRepository {

    @Inject
    lateinit var mapper: TvDetailsResponseMapper

    override suspend fun getTvDetails(id: Int): TvDetailsEntity {
        return mapper.map(service.getTvDetails(id, "images,videos"))
    }
}