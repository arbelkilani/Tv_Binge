package com.arbelkilani.binge.tv.common.domain.usecase

import android.util.Log
import androidx.lifecycle.LiveData
import com.arbelkilani.binge.tv.common.domain.repository.NetworkRepository
import javax.inject.Inject

class ObserveNetworkReachabilityUseCase @Inject constructor() {

    @Inject
    lateinit var networkRepository: NetworkRepository

    fun invoke(): LiveData<Boolean> {
        Log.i("Network**", "invoke")
        return networkRepository.observeNetworkReachability()
    }
}