package com.arbelkilani.binge.tv.feature.details.domain.usecase

import com.arbelkilani.binge.tv.feature.details.domain.mapper.KeywordsEntityMapper
import com.arbelkilani.binge.tv.feature.details.domain.repositories.TvDetailsRepository
import com.arbelkilani.binge.tv.feature.details.presentation.entities.Keywords
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetKeywordsUseCase @Inject constructor() {

    @Inject
    lateinit var tvDetailsRepository: TvDetailsRepository

    @Inject
    lateinit var keywordsEntityMapper: KeywordsEntityMapper

    suspend fun invoke(id: Int): Flow<List<Keywords>> {
        return tvDetailsRepository.getKeywords(id)
            .flowOn(Dispatchers.IO)
            .map { list ->
                list.map { entity ->
                    keywordsEntityMapper.map(entity)
                }
            }
    }
}