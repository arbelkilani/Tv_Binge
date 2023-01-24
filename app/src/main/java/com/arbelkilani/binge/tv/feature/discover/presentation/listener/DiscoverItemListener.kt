package com.arbelkilani.binge.tv.feature.discover.presentation.listener

import com.arbelkilani.binge.tv.feature.discover.presentation.model.Person
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Provider
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv

interface DiscoverItemListener {
    fun onTvClicked(tv: Tv?)
    fun onPersonClicked(person: Person?)
    fun onLinkToProviderClicked(provider: Provider?)
}