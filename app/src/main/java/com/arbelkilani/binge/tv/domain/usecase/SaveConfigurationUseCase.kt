package com.arbelkilani.binge.tv.domain.usecase

import com.arbelkilani.binge.tv.domain.repositories.ConfigurationRepository
import javax.inject.Inject

class SaveConfigurationUseCase @Inject constructor() {

    @Inject
    lateinit var configurationRepository: ConfigurationRepository

    suspend fun execute() {
    }
}