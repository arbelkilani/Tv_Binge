package com.arbelkilani.binge.tv.feature.discover.domain.mapper

import com.arbelkilani.binge.tv.common.domain.mapper.GenreEntityMapper
import com.arbelkilani.binge.tv.feature.discover.domain.entity.TvEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv
import javax.inject.Inject

class TvEntityMapper @Inject constructor() {

    @Inject
    lateinit var providerEntityMapper: ProviderEntityMapper

    @Inject
    lateinit var genreEntityMapper: GenreEntityMapper

    fun map(response: TvEntity) = Tv(
        id = response.id,
        name = response.name,
        poster = response.poster,
        backdrop = response.backdrop,
        genres = response.genres.map { genreEntityMapper.map(it) },
        voteAverage = response.voteAverage,
        firstAirDate = response.firstAirDate,
        providers = response.providers.map { providerEntityMapper.map(it) }
    )
}