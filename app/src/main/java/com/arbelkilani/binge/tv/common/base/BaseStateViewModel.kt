package com.arbelkilani.binge.tv.common.base

import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseStateViewModel<T>(initialState: T) : StateViewModel<T>,
    BaseViewModel() {

    override val viewState = MutableStateFlow(initialState)

    override fun updateState(handler: (T) -> T) {
        val currentState = viewState.value
        val newState = handler.invoke(currentState)
        viewState.value = newState
    }

    override fun handleError(exception: Exception) {}
}