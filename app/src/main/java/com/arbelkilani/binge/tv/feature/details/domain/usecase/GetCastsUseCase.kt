package com.arbelkilani.binge.tv.feature.details.domain.usecase

import com.arbelkilani.binge.tv.feature.details.domain.mapper.CastEntityMapper
import com.arbelkilani.binge.tv.feature.details.domain.repositories.TvDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCastsUseCase @Inject constructor() {

    @Inject
    lateinit var detailsRepository: TvDetailsRepository

    @Inject
    lateinit var castEntityMapper: CastEntityMapper

    suspend fun invoke(id: Int) = detailsRepository.getCasts(id)
        .flowOn(Dispatchers.IO)
        .map { list ->
            list.map { entity ->
                castEntityMapper.map(entity)
            }
        }
}