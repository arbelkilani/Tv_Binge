package com.arbelkilani.binge.tv.feature.discover.presentation.listener

import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity

interface DiscoverItemListener {
    fun onTvClicked(tvEntity: TvEntity)
    fun onProviderClicked(watchProviderEntity: WatchProviderEntity)
    fun onGenreClicked(genreEntity: GenreEntity)
}