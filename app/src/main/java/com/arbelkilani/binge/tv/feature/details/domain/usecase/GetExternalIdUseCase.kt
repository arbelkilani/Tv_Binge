package com.arbelkilani.binge.tv.feature.details.domain.usecase

import com.arbelkilani.binge.tv.feature.details.domain.mapper.ExternalIdEntityMapper
import com.arbelkilani.binge.tv.feature.details.domain.repositories.TvDetailsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetExternalIdUseCase @Inject constructor() {

    @Inject
    lateinit var tvDetailsRepository: TvDetailsRepository

    @Inject
    lateinit var externalIdEntityMapper: ExternalIdEntityMapper

    suspend fun invoke(id: Int) = tvDetailsRepository.getExternalId(id).map {
        externalIdEntityMapper.map(it)
    }
}