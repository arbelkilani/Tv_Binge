package com.arbelkilani.binge.tv.feature.details.domain.repositories

import com.arbelkilani.binge.tv.feature.details.domain.entities.TvDetailsEntity

interface TvDetailsRepository {
    suspend fun getTvDetails(id: Int): TvDetailsEntity
}