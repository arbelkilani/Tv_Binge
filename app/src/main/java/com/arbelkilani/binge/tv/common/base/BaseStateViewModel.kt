package com.arbelkilani.binge.tv.common.base

import androidx.lifecycle.MutableLiveData

abstract class BaseStateViewModel<T>(initialState: T) : StateViewModel<T>,
    BaseViewModel() {

    override val viewState = MutableLiveData(initialState)

    override fun updateState(handler: (T) -> T) {
        val currentState = viewState.value!!
        val newState = handler.invoke(currentState)
        viewState.postValue(newState)
    }
}