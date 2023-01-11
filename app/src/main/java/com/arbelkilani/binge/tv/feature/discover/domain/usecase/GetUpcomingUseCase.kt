package com.arbelkilani.binge.tv.feature.discover.domain.usecase

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingUseCase @Inject constructor() {

    @Inject
    lateinit var discoverRepository: DiscoverRepository

    suspend fun invoke(): Flow<PagingData<TvEntity>> {
        return discoverRepository.getUpcoming()
    }
}