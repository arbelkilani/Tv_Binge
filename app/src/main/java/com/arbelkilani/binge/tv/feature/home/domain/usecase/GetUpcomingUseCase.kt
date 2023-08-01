package com.arbelkilani.binge.tv.feature.home.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.arbelkilani.binge.tv.feature.home.domain.mapper.TvEntityMapper
import com.arbelkilani.binge.tv.feature.home.domain.repository.HomeRepository
import com.arbelkilani.binge.tv.feature.home.presentation.model.Tv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUpcomingUseCase @Inject constructor() {

    @Inject
    lateinit var homeRepository: HomeRepository

    @Inject
    lateinit var mapper: TvEntityMapper

    suspend fun invoke(): Flow<PagingData<Tv>> {
        return homeRepository.getUpcoming()
            .map { pagingData -> pagingData.map { tvEntity -> mapper.map(tvEntity) } }
            .flowOn(Dispatchers.IO)
    }
}