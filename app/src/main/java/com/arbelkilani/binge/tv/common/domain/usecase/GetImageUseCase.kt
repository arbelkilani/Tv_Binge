package com.arbelkilani.binge.tv.common.domain.usecase

import com.arbelkilani.binge.tv.common.data.enum.ImageSize
import com.arbelkilani.binge.tv.common.domain.repository.ResourcesRepository
import javax.inject.Inject

class GetImageUseCase @Inject constructor() {

    @Inject
    lateinit var resourcesRepository: ResourcesRepository

    suspend fun invoke(endpoint: String?, size: ImageSize): String {
        return endpoint?.let {
            resourcesRepository.getBaseImage(size) + it
        } ?: run { "" }
    }
}