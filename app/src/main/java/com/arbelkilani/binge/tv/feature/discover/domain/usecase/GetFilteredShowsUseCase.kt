package com.arbelkilani.binge.tv.feature.discover.domain.usecase

import androidx.paging.map
import com.arbelkilani.binge.tv.common.presentation.model.Genre
import com.arbelkilani.binge.tv.common.presentation.model.Provider
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import com.arbelkilani.binge.tv.feature.home.domain.mapper.TvEntityMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFilteredShowsUseCase @Inject constructor() {

    @Inject
    lateinit var repository: DiscoverRepository

    @Inject
    lateinit var mapper: TvEntityMapper

    suspend fun invoke(genres: MutableList<Genre>, providers: MutableList<Provider>) = repository.filterShows(genres, providers)
        .map { pagingData -> pagingData.map { tvEntity -> mapper.map(tvEntity) } }
        .flowOn(Dispatchers.IO)
}