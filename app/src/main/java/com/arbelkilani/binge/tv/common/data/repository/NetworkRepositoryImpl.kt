package com.arbelkilani.binge.tv.common.data.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.binge.tv.common.domain.repository.NetworkRepository
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    application: Application
) : NetworkRepository {

    private var connectivityManager: ConnectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _state = MutableLiveData(false)
    val state: LiveData<Boolean>
        get() = _state

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.i("Network**", "onAvailable")
            _state.postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Log.i("Network**", "onLost")
            _state.postValue(false)
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {

            Log.i("Network**", "onCapabilitiesChanged")


            val isInternet =
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            val isValidated =
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            if (isValidated) {
                Log.i(
                    "Network**",
                    "hasCapability: $network $networkCapabilities"
                )
            } else {
                Log.i(
                    "Network**",
                    "Network has No Connection Capability: $network $networkCapabilities"
                )
            }
            _state.postValue(isInternet && isValidated)
        }
    }

    override fun observeNetworkReachability(): LiveData<Boolean> {
        Log.i("Network**", "observe network reachability")
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            networkCallback
        )
        Log.i("Network**", "observe network reachability _state : ${_state.value}")

        return _state
    }
}