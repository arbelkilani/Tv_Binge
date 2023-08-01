package com.arbelkilani.binge.tv.feature.details.domain.usecase

import com.arbelkilani.binge.tv.feature.details.domain.mapper.ContentRatingsEntityMapper
import com.arbelkilani.binge.tv.feature.details.domain.repositories.TvDetailsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetContentRatingUseCase @Inject constructor() {

    @Inject
    lateinit var tvDetailsRepository: TvDetailsRepository

    @Inject
    lateinit var contentRatingsEntityMapper: ContentRatingsEntityMapper

    suspend fun invoke(id: Int) = tvDetailsRepository.getContentRatings(id).map { entity ->
        entity?.let { it1 -> contentRatingsEntityMapper.map(it1) }
    }
}