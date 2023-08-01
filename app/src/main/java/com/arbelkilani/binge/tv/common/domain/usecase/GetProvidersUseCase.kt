package com.arbelkilani.binge.tv.common.domain.usecase

import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.domain.mapper.ProviderEntityMapper
import com.arbelkilani.binge.tv.common.domain.repository.ProviderRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProvidersUseCase @Inject constructor() {

    @Inject
    lateinit var providerRepository: ProviderRepository

    @Inject
    lateinit var providerEntityMapper: ProviderEntityMapper

    suspend fun invoke() = flow {
        emit(PagingData.from(providerRepository.getProviders().map { entity ->
            providerEntityMapper.map(entity)
        }))
    }
}