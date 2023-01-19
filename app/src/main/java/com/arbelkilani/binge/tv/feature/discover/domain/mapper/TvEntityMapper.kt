package com.arbelkilani.binge.tv.feature.discover.domain.mapper

import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.entities.Tv
import javax.inject.Inject

class TvEntityMapper @Inject constructor() {

    fun map(response: TvEntity) = Tv(
        id = response.id,
        name = response.name,
        poster = response.poster,
        backdrop = response.backdrop,
        genres = response.genres,
        voteAverage = response.voteAverage,
        firstAirDate = response.firstAirDate
    )
}