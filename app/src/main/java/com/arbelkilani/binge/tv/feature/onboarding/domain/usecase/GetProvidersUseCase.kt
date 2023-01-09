package com.arbelkilani.binge.tv.feature.onboarding.domain.usecase

import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.onboarding.domain.repository.OnBoardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProvidersUseCase @Inject constructor() {

    @Inject
    lateinit var onBoardingRepository: OnBoardingRepository

    suspend fun invoke(): Flow<List<WatchProviderEntity>> {
        return onBoardingRepository.getProviders()
    }
}