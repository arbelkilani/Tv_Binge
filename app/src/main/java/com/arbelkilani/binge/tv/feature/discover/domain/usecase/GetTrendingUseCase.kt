package com.arbelkilani.binge.tv.feature.discover.domain.usecase

import com.arbelkilani.binge.tv.feature.discover.data.entities.TrendingResponse
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingUseCase @Inject constructor() {

    @Inject
    lateinit var discoverRepository: DiscoverRepository

    suspend fun invoke(): Flow<List<TrendingResponse>> {
        return discoverRepository.getTrending()
    }
}