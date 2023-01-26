package com.arbelkilani.binge.tv.feature.search.domain.usecase

import com.arbelkilani.binge.tv.common.domain.mapper.GenreEntityMapper
import com.arbelkilani.binge.tv.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGenresUseCase @Inject constructor() {

    @Inject
    lateinit var searchRepository: SearchRepository

    @Inject
    lateinit var genreEntityMapper: GenreEntityMapper

    suspend fun invoke() = searchRepository.getGenres().map { list ->
        list.map {
            genreEntityMapper.map(it)
        }
    }
}