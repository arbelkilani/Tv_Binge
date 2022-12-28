package com.arbelkilani.binge.tv.common.base

import kotlinx.coroutines.flow.StateFlow

interface StateViewModel<T> {
    val viewState: StateFlow<T>
    fun updateState(handler: (T) -> (T))
    fun handleError(exception: Exception)
}