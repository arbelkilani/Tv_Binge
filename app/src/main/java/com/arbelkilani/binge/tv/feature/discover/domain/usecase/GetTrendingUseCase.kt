package com.arbelkilani.binge.tv.feature.discover.domain.usecase

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.domain.mapper.TvEntityMapper
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTrendingUseCase @Inject constructor() {

    @Inject
    lateinit var discoverRepository: DiscoverRepository

    @Inject
    lateinit var mapper: TvEntityMapper

    suspend fun invoke(): Flow<PagingData<TvEntity>> {
        return discoverRepository.getTrending()
            .flowOn(Dispatchers.IO)
    }
}