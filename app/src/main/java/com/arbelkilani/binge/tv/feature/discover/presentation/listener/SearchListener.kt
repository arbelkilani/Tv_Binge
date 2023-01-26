package com.arbelkilani.binge.tv.feature.discover.presentation.listener

import com.arbelkilani.binge.tv.common.presentation.model.Genre
import com.arbelkilani.binge.tv.common.presentation.model.Provider

interface SearchListener {
    fun onGenreSelected(genre: Genre?)
    fun onProviderSelected(provider: Provider?)
}