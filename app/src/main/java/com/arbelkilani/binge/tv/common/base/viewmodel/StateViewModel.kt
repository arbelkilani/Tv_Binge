package com.arbelkilani.binge.tv.common.base.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface StateViewModel<T> {
    val viewState: StateFlow<T>
    fun updateState(handler: (T) -> (T))
}