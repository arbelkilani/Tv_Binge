package com.arbelkilani.binge.tv.domain.usecase

import com.arbelkilani.binge.tv.domain.repositories.ConfigurationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIsFirstRunUseCase @Inject constructor() {

    @Inject
    lateinit var configurationRepository: ConfigurationRepository

    suspend fun execute(): Flow<Boolean> {
        return configurationRepository.isFirstRun()
    }
}