package com.arbelkilani.binge.tv.feature.details.data.repositories

import com.arbelkilani.binge.tv.common.source.remote.ApiService
import com.arbelkilani.binge.tv.feature.details.domain.repositories.TvDetailsRepository
import javax.inject.Inject

class TvDetailsRepositoryImpl @Inject constructor(
    private val service: ApiService
) : TvDetailsRepository {

    override suspend fun getTvDetails(id: Int) {
        service.getTvDetails(id, "")
    }
}