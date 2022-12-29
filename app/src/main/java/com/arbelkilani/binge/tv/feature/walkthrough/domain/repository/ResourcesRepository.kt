package com.arbelkilani.binge.tv.feature.walkthrough.domain.repository

interface ResourcesRepository {
    suspend fun execute()
    suspend fun getBackdrop(): String?
    suspend fun getPoster(): String?
}