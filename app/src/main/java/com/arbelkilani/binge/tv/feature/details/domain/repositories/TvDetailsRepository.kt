package com.arbelkilani.binge.tv.feature.details.domain.repositories

interface TvDetailsRepository {
    suspend fun getTvDetails(id: Int)
}