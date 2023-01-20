package com.arbelkilani.binge.tv.feature.discover.data.mapper

import com.arbelkilani.binge.tv.common.data.enum.ImageSize
import com.arbelkilani.binge.tv.common.domain.usecase.GetGenresByIdsUseCase
import com.arbelkilani.binge.tv.common.domain.usecase.GetImageUseCase
import com.arbelkilani.binge.tv.feature.discover.data.entities.TvResponse
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import javax.inject.Inject

class TvResponseMapper @Inject constructor() {

    @Inject
    lateinit var getGenresByIdsUseCase: GetGenresByIdsUseCase

    @Inject
    lateinit var getImageUseCase: GetImageUseCase

    suspend fun map(response: TvResponse) = TvEntity(
        id = response.id,
        name = response.name,
        poster = getImageUseCase.invoke(endpoint = response.poster, size = ImageSize.POSTER_W154),
        backdrop = getImageUseCase.invoke(
            endpoint = response.backdrop, size = ImageSize.BACKDROP_W780
        ),
        genres = getGenresByIdsUseCase.invoke(response.genres),
        voteAverage = response.voteAverage,
        firstAirDate = response.firstAirDate
    )
}