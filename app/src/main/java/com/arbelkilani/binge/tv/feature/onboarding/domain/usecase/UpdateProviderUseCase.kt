package com.arbelkilani.binge.tv.feature.onboarding.domain.usecase

import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.onboarding.domain.repository.ProviderSelectionRepository
import javax.inject.Inject

class UpdateProviderUseCase @Inject constructor() {

    @Inject
    lateinit var providerSelectionRepository: ProviderSelectionRepository

    suspend fun invoke(provider: WatchProviderEntity){
        return providerSelectionRepository.updateProviderState(provider)
    }

}