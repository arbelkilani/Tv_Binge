package com.arbelkilani.binge.tv.feature.discover.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.arbelkilani.binge.tv.feature.discover.domain.mapper.PersonEntityMapper
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import com.arbelkilani.binge.tv.feature.discover.presentation.entities.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrendingPersonUseCase @Inject constructor() {

    @Inject
    lateinit var discoverRepository: DiscoverRepository

    @Inject
    lateinit var personEntityMapper: PersonEntityMapper

    suspend fun invoke(): Flow<PagingData<Person>> = discoverRepository.getTrendingPerson()
        .map { pagingData -> pagingData.map { entity ->
            personEntityMapper.map(entity) } }
        .flowOn(Dispatchers.IO)
}