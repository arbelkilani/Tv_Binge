package com.arbelkilani.binge.tv.feature.home.domain.usecase

import com.arbelkilani.binge.tv.common.domain.repository.ConfigurationRepository
import javax.inject.Inject

class ToggleIsFirstRunStateUseCase @Inject constructor() {

    @Inject
    lateinit var configurationRepository: ConfigurationRepository

    suspend fun invoke() {
        configurationRepository.setNoFirstRun()
    }
}