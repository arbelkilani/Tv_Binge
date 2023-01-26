package com.arbelkilani.binge.tv.feature.discover.domain.repository

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.common.presentation.model.Genre
import com.arbelkilani.binge.tv.common.presentation.model.Provider
import com.arbelkilani.binge.tv.feature.home.domain.entity.TvEntity
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {
    suspend fun getGenres(): Flow<List<GenreEntity>>
    suspend fun discover(): Flow<PagingData<TvEntity>>
    suspend fun filterShows(
        genres: MutableList<Genre>,
        providers: MutableList<Provider>
    ): Flow<PagingData<TvEntity>>
}