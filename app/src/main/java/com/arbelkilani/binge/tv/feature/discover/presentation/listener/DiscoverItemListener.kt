package com.arbelkilani.binge.tv.feature.discover.presentation.listener

import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv

interface DiscoverItemListener {
    fun onTvClicked(tv: Tv?)
}