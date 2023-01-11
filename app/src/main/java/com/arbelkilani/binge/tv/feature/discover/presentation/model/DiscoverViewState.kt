package com.arbelkilani.binge.tv.feature.discover.presentation.model

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity

sealed class DiscoverViewState {
    object Start : DiscoverViewState()
    object Loading : DiscoverViewState()
    data class Error(val exception: Exception) : DiscoverViewState()
    data class Data(
        val trending: PagingData<Tv> = PagingData.empty(),
        val startingThisMonth: PagingData<Tv> = PagingData.empty(),
        val basedOnProvider: PagingData<Tv> = PagingData.empty(),
        val free: PagingData<Tv> = PagingData.empty(),
        val providers: List<WatchProviderEntity> = emptyList(),
        val genres: List<GenreEntity> = emptyList(),
        val basedOnGenres: PagingData<Tv> = PagingData.empty(),
        val upcoming: PagingData<Tv> = PagingData.empty()
    ) : DiscoverViewState()
}