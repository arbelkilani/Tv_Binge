package com.arbelkilani.binge.tv.common.domain.usecase

import com.arbelkilani.binge.tv.common.domain.mapper.ProviderEntityMapper
import com.arbelkilani.binge.tv.common.domain.repository.ProviderRepository
import com.arbelkilani.binge.tv.common.presentation.model.Provider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProvidersUseCase @Inject constructor() {

    @Inject
    lateinit var providerRepository: ProviderRepository

    @Inject
    lateinit var providerEntityMapper: ProviderEntityMapper

    suspend fun invoke(): Flow<List<Provider>> {
        return providerRepository.getProviders().map { list ->
            list.map { entity ->
                providerEntityMapper.map(entity)
            }
        }
    }
}