package com.arbelkilani.binge.tv.common.base

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData

class ConnectionLiveData(private val connectivityManager: ConnectivityManager) :
    LiveData<Boolean>() {

    constructor(application: Application) : this(
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    )

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            Log.i("TAG**", "onAvailable")
            super.onAvailable(network)
            postValue(true)
        }

        override fun onLost(network: Network) {
            Log.i("TAG**", "onAvailable")
            super.onLost(network)
            postValue(false)
        }
    }

    override fun onActive() {
        super.onActive()
        Log.i("TAG**", "onActive")
        val builder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        Log.i("TAG**", "onInactive")
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}