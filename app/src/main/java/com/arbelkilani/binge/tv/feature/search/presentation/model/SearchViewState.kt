package com.arbelkilani.binge.tv.feature.search.presentation.model

sealed class SearchViewState {
    object Start : SearchViewState()
    data class Error(val exception: Exception) : SearchViewState()
    object Loaded : SearchViewState()
}