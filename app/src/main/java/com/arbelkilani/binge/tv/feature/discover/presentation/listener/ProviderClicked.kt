package com.arbelkilani.binge.tv.feature.discover.presentation.listener

import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity

interface ProviderClicked {
    fun onProviderClicked(watchProviderEntity: WatchProviderEntity)
}