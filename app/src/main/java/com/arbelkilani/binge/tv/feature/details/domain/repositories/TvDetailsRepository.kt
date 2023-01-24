package com.arbelkilani.binge.tv.feature.details.domain.repositories

import com.arbelkilani.binge.tv.feature.details.domain.entities.CastEntity
import com.arbelkilani.binge.tv.feature.details.domain.entities.ExternalIdEntity
import com.arbelkilani.binge.tv.feature.details.domain.entities.KeywordsEntity
import com.arbelkilani.binge.tv.feature.details.domain.entities.TvDetailsEntity
import kotlinx.coroutines.flow.Flow

interface TvDetailsRepository {
    suspend fun getTvDetails(id: Int): Flow<TvDetailsEntity>
    suspend fun getKeywords(id: Int): Flow<List<KeywordsEntity>>
    suspend fun getCasts(id: Int): Flow<List<CastEntity>>
    suspend fun getExternalId(id: Int): Flow<ExternalIdEntity>
}