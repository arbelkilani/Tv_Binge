package com.arbelkilani.binge.tv.common.domain.usecase

import com.arbelkilani.binge.tv.common.domain.entity.ProviderEntity
import com.arbelkilani.binge.tv.common.domain.mapper.ProviderEntityMapper
import com.arbelkilani.binge.tv.common.domain.repository.ProviderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProvidersUseCase @Inject constructor() {

    @Inject
    lateinit var providerRepository: ProviderRepository

    @Inject
    lateinit var providerEntityMapper: ProviderEntityMapper

    suspend fun invoke() : Flow<List<ProviderEntity>> {
        return providerRepository.getProviders()
    }
}