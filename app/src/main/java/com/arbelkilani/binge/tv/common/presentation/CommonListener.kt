package com.arbelkilani.binge.tv.common.presentation

import com.arbelkilani.binge.tv.common.presentation.model.Person

interface CommonListener {
    fun onPersonClicked(person: Person?)
}