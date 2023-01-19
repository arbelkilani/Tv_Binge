package com.arbelkilani.binge.tv.common.domain.usecase

import androidx.lifecycle.LiveData
import com.arbelkilani.binge.tv.common.domain.repository.NetworkRepository
import javax.inject.Inject

class ObserveNetworkReachabilityUseCase @Inject constructor() {

    @Inject
    lateinit var networkRepository: NetworkRepository

    suspend fun invoke(): LiveData<Boolean> {
        return networkRepository.observeNetworkReachability()
    }
}