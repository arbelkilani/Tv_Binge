package com.arbelkilani.binge.tv.feature.details.domain.usecase

import com.arbelkilani.binge.tv.feature.details.domain.repositories.TvDetailsRepository
import javax.inject.Inject

class GetTvDetailsDataUseCase @Inject constructor() {

    @Inject
    lateinit var tvDetailsRepository: TvDetailsRepository

    suspend fun invoke(id: Int) {
        tvDetailsRepository.getTvDetails(id)
    }

}