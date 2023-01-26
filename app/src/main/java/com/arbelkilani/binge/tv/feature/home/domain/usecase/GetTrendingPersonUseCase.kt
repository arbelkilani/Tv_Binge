package com.arbelkilani.binge.tv.feature.home.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.arbelkilani.binge.tv.common.presentation.model.Person
import com.arbelkilani.binge.tv.feature.home.domain.mapper.PersonEntityMapper
import com.arbelkilani.binge.tv.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrendingPersonUseCase @Inject constructor() {

    @Inject
    lateinit var homeRepository: HomeRepository

    @Inject
    lateinit var personEntityMapper: PersonEntityMapper

    suspend fun invoke(): Flow<PagingData<Person>> = homeRepository.getTrendingPerson()
        .map { pagingData -> pagingData.map { entity ->
            personEntityMapper.map(entity) } }
        .flowOn(Dispatchers.IO)
}