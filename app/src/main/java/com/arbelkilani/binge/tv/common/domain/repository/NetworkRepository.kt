package com.arbelkilani.binge.tv.common.domain.repository

import androidx.lifecycle.LiveData

interface NetworkRepository {
    fun observeNetworkReachability(): LiveData<Boolean>
}