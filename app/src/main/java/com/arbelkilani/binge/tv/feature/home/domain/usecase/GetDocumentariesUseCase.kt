package com.arbelkilani.binge.tv.feature.home.domain.usecase

import androidx.paging.map
import com.arbelkilani.binge.tv.feature.home.domain.mapper.TvEntityMapper
import com.arbelkilani.binge.tv.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDocumentariesUseCase @Inject constructor() {

    @Inject
    lateinit var homeRepository: HomeRepository

    @Inject
    lateinit var mapper: TvEntityMapper

    suspend fun invoke() = homeRepository.getDocumentaries()
        .map { pagingData -> pagingData.map { tvEntity -> mapper.map(tvEntity) } }
        .flowOn(Dispatchers.IO)
}