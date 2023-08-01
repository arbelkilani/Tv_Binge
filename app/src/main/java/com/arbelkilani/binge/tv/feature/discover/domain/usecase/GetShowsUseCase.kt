package com.arbelkilani.binge.tv.feature.discover.domain.usecase

import androidx.paging.map
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import com.arbelkilani.binge.tv.feature.home.domain.mapper.TvEntityMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetShowsUseCase @Inject constructor() {

    @Inject
    lateinit var repository: DiscoverRepository

    @Inject
    lateinit var mapper: TvEntityMapper

    suspend fun invoke() = repository.discover()
        .map { pagingData -> pagingData.map { tvEntity -> mapper.map(tvEntity) } }
        .flowOn(Dispatchers.IO)
}