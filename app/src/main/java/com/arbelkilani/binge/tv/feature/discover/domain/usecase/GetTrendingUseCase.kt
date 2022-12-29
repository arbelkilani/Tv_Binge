package com.arbelkilani.binge.tv.feature.discover.domain.usecase

import com.arbelkilani.binge.tv.feature.discover.domain.entities.TrendingEntity
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingUseCase @Inject constructor() {

    @Inject
    lateinit var discoverRepository: DiscoverRepository

    suspend fun invoke(): Flow<List<TrendingEntity>> {
        return discoverRepository.getTrending()
    }
}