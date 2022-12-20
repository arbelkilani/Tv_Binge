package com.arbelkilani.binge.tv.domain.usecase

import com.arbelkilani.binge.tv.domain.entities.WatchProviderEntity
import com.arbelkilani.binge.tv.domain.repositories.ConfigurationRepository
import com.arbelkilani.binge.tv.domain.repositories.WatchProvidersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWatchProvidersUseCase @Inject constructor() {

    @Inject
    lateinit var watchProvidersRepository: WatchProvidersRepository

    @Inject
    lateinit var configurationRepository: ConfigurationRepository

    suspend fun execute(): Flow<List<WatchProviderEntity>> {
        return watchProvidersRepository.getProviders()
    }
}