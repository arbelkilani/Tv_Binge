package com.arbelkilani.binge.tv.feature.discover.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.arbelkilani.binge.tv.feature.discover.domain.mapper.TvEntityMapper
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUpcomingUseCase @Inject constructor() {

    @Inject
    lateinit var discoverRepository: DiscoverRepository

    @Inject
    lateinit var mapper: TvEntityMapper

    suspend fun invoke(): Flow<PagingData<Tv>> {
        return discoverRepository.getUpcoming()
            .map { pagingData -> pagingData.map { tvEntity -> mapper.map(tvEntity) } }
            .flowOn(Dispatchers.IO)
    }
}