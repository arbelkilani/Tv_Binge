package com.arbelkilani.binge.tv.feature.walkthrough.domain.usecase

import com.arbelkilani.binge.tv.feature.walkthrough.domain.repository.ResourcesRepository
import javax.inject.Inject

class SaveResourcesUseCase @Inject constructor() {

    @Inject
    lateinit var resourcesRepository: ResourcesRepository

    suspend fun execute() {
        resourcesRepository.execute()
    }
}