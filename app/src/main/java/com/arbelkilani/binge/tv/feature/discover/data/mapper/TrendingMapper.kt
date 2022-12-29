package com.arbelkilani.binge.tv.feature.discover.data.mapper

import com.arbelkilani.binge.tv.common.domain.usecase.GetBackdropUseCase
import com.arbelkilani.binge.tv.common.domain.usecase.GetGenresByIdsUseCase
import com.arbelkilani.binge.tv.common.domain.usecase.GetPosterUseCase
import com.arbelkilani.binge.tv.feature.discover.data.entities.TrendingResponse
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TrendingEntity
import javax.inject.Inject

class TrendingMapper @Inject constructor() {

    @Inject
    lateinit var getGenresByIdsUseCase: GetGenresByIdsUseCase

    @Inject
    lateinit var getBackdropUseCase: GetBackdropUseCase

    @Inject
    lateinit var getPosterUseCase: GetPosterUseCase

    suspend fun map(response: TrendingResponse) = TrendingEntity(
        id = response.id,
        name = response.name,
        poster = getPosterUseCase.invoke(response.poster),
        backdrop = getBackdropUseCase.invoke(response.backdrop),
        genres = getGenresByIdsUseCase.invoke(response.genres)
    )
}