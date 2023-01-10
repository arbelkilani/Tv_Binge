package com.arbelkilani.binge.tv.feature.discover.domain.usecase

import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteGenresUseCase @Inject constructor() {

    @Inject
    lateinit var discoverRepository: DiscoverRepository

    suspend fun invoke(): Flow<List<GenreEntity>?> {
        return discoverRepository.getFavoriteGenres()
    }
}