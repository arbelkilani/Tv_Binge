package com.arbelkilani.binge.tv.feature.discover.presentation.listener

import com.arbelkilani.binge.tv.feature.discover.presentation.entities.Person
import com.arbelkilani.binge.tv.feature.discover.presentation.entities.Tv

interface DiscoverItemListener {
    fun onTvClicked(tv: Tv?)
    fun onPersonClicked(person: Person?)
}