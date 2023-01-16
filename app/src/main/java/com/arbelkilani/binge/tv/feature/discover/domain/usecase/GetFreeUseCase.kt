package com.arbelkilani.binge.tv.feature.discover.domain.usecase

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFreeUseCase @Inject constructor() {

    @Inject
    lateinit var discoverRepository: DiscoverRepository

    suspend fun invoke(): Flow<PagingData<TvEntity>> {
        return discoverRepository.getFree()
            .flowOn(Dispatchers.IO)
    }
}