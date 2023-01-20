package com.arbelkilani.binge.tv.feature.onboarding.domain.usecase

import com.arbelkilani.binge.tv.common.domain.entities.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.onboarding.domain.repository.OnBoardingRepository
import javax.inject.Inject

class UpdateProviderUseCase @Inject constructor() {

    @Inject
    lateinit var onBoardingRepository: OnBoardingRepository

    suspend fun invoke(provider: WatchProviderEntity) {
        return onBoardingRepository.updateProviderState(provider)
    }
}