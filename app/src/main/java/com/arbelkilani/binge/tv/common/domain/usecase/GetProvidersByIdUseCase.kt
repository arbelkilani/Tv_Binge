package com.arbelkilani.binge.tv.common.domain.usecase

import com.arbelkilani.binge.tv.common.domain.entity.ProviderEntity
import com.arbelkilani.binge.tv.common.domain.repository.ProviderRepository
import javax.inject.Inject

class GetProvidersByIdUseCase @Inject constructor() {

    @Inject
    lateinit var providerRepository: ProviderRepository

    suspend fun invoke(id: Int): List<ProviderEntity> {
        return providerRepository.getProvidersById(id)
    }
}