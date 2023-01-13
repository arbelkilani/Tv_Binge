package com.arbelkilani.binge.tv.feature.details.domain.usecase

import com.arbelkilani.binge.tv.feature.details.domain.mapper.TvDetailsEntityMapper
import com.arbelkilani.binge.tv.feature.details.domain.repositories.TvDetailsRepository
import com.arbelkilani.binge.tv.feature.details.presentation.entities.TvDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTvDetailsDataUseCase @Inject constructor() {

    @Inject
    lateinit var tvDetailsRepository: TvDetailsRepository

    @Inject
    lateinit var mapper: TvDetailsEntityMapper

    suspend fun invoke(id: Int): Flow<TvDetails> {
        return tvDetailsRepository.getTvDetails(id).map {
            mapper.map(it)
        }
    }
}