package com.arbelkilani.binge.tv.common.data.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.binge.tv.common.domain.repository.NetworkRepository
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val application: Application
) : NetworkRepository {

    private val _state = MutableLiveData<Boolean>(false)
    val state: MutableLiveData<Boolean>
        get() = _state

    private val connectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observeNetworkReachability(): LiveData<Boolean> {
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            networkCallback
        )
        return _state
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _state.postValue(true)
        }

        override fun onLost(network: Network) {
            _state.postValue(false)
        }
    }
}