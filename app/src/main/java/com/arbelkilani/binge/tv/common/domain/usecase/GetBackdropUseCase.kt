package com.arbelkilani.binge.tv.common.domain.usecase

import com.arbelkilani.binge.tv.feature.walkthrough.domain.repository.ResourcesRepository
import javax.inject.Inject

class GetBackdropUseCase @Inject constructor() {

    @Inject
    lateinit var resourcesRepository: ResourcesRepository

    suspend fun invoke(endpoint: String?): String {
        return resourcesRepository.getBackdrop() + endpoint
    }
}