package com.arbelkilani.binge.tv.feature.home.presentation.listener

import com.arbelkilani.binge.tv.common.presentation.model.Person
import com.arbelkilani.binge.tv.common.presentation.model.Provider
import com.arbelkilani.binge.tv.feature.home.presentation.model.Tv

interface HomeListener {
    fun onTvClicked(tv: Tv?)
    fun onPersonClicked(person: Person?)
    fun onLinkToProviderClicked(provider: Provider?)
}