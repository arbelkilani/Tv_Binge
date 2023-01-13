package com.arbelkilani.binge.tv.feature.details.domain.usecase

import com.arbelkilani.binge.tv.feature.details.domain.entities.TvDetailsEntity
import com.arbelkilani.binge.tv.feature.details.domain.repositories.TvDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTvDetailsDataUseCase @Inject constructor() {

    @Inject
    lateinit var tvDetailsRepository: TvDetailsRepository

    suspend fun invoke(id: Int): Flow<TvDetailsEntity> {
        return tvDetailsRepository.getTvDetails(id)
    }

}