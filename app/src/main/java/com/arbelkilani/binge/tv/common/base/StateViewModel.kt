package com.arbelkilani.binge.tv.common.base

import androidx.lifecycle.LiveData

interface StateViewModel<T> {
    val viewState: LiveData<T>
    fun updateState(handler: (T) -> (T))
}