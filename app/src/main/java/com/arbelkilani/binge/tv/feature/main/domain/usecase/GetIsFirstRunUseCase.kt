package com.arbelkilani.binge.tv.feature.main.domain.usecase

import com.arbelkilani.binge.tv.common.domain.repository.ConfigurationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIsFirstRunUseCase @Inject constructor() {

    @Inject
    lateinit var configurationRepository: ConfigurationRepository

    suspend fun execute(): Flow<Boolean> {
        return configurationRepository.isFirstRun()
    }
}