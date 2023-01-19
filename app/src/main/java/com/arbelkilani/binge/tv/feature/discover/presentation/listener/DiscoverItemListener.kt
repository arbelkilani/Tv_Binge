package com.arbelkilani.binge.tv.feature.discover.presentation.listener

import com.arbelkilani.binge.tv.feature.discover.presentation.entities.Tv

interface DiscoverItemListener {
    fun onTvClicked(tv: Tv?)
}