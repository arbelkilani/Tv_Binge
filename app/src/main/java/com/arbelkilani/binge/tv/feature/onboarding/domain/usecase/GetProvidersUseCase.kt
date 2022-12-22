package com.arbelkilani.binge.tv.feature.onboarding.domain.usecase

import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.onboarding.domain.repository.ProviderSelectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProvidersUseCase @Inject constructor() {

    @Inject
    lateinit var providerSelectionRepository: ProviderSelectionRepository

    suspend fun getProviders(): Flow<List<WatchProviderEntity>> {
        return providerSelectionRepository.getProviders()
    }
}