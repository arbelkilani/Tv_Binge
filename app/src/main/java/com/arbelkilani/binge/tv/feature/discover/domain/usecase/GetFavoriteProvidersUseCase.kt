package com.arbelkilani.binge.tv.feature.discover.domain.usecase

import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteProvidersUseCase @Inject constructor() {

    @Inject
    lateinit var discoverRepository: DiscoverRepository

    suspend fun invoke(): Flow<List<WatchProviderEntity>?> {
        return discoverRepository.getFavoriteProviders()
    }

}