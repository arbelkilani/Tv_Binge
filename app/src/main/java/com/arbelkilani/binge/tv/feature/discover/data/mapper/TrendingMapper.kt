package com.arbelkilani.binge.tv.feature.discover.data.mapper

import com.arbelkilani.binge.tv.common.domain.usecase.GetGenresByIdsUseCase
import com.arbelkilani.binge.tv.feature.discover.data.entities.TrendingResponse
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TrendingEntity
import javax.inject.Inject

class TrendingMapper @Inject constructor() {

    @Inject
    lateinit var getGenresByIdsUseCase: GetGenresByIdsUseCase

    suspend fun map(response: TrendingResponse) = TrendingEntity(
        id = response.id,
        name = response.name,
        poster = response.poster,
        backdrop = response.backdrop,
        genres = getGenresByIdsUseCase.invoke(response.genres)
    )
}