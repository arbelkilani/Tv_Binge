package com.arbelkilani.binge.tv.feature.discover.presentation.model

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity

sealed class DiscoverViewState {
    object Start : DiscoverViewState()
    object Loading : DiscoverViewState()
    data class Error(val exception: Exception) : DiscoverViewState()
    data class Data(
        val trending: PagingData<TvEntity> = PagingData.empty(),
        val startingThisMonth: PagingData<TvEntity> = PagingData.empty(),
        val basedOnProvider: PagingData<TvEntity> = PagingData.empty(),
        val free: PagingData<TvEntity> = PagingData.empty(),
        val providers: List<WatchProviderEntity> = emptyList(),
        val genres: List<GenreEntity> = emptyList(),
        val basedOnGenres: PagingData<TvEntity> = PagingData.empty()
    ) : DiscoverViewState()
}