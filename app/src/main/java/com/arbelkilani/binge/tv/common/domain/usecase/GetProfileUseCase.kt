package com.arbelkilani.binge.tv.common.domain.usecase

import com.arbelkilani.binge.tv.common.data.enum.ImageSize
import com.arbelkilani.binge.tv.feature.walkthrough.domain.repository.ResourcesRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor() {

    @Inject
    lateinit var resourcesRepository: ResourcesRepository

    suspend fun invoke(size: ImageSize): String {
        return resourcesRepository.getProfile(size)
    }
}