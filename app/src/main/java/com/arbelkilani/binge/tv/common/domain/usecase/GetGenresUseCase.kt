package com.arbelkilani.binge.tv.common.domain.usecase

import com.arbelkilani.binge.tv.common.domain.mapper.GenreEntityMapper
import com.arbelkilani.binge.tv.common.domain.repository.GenreRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor() {

    @Inject
    lateinit var genreRepository: GenreRepository

    @Inject
    lateinit var genreEntityMapper: GenreEntityMapper

    suspend fun invoke() = flow {
        emit(genreRepository.getGenres().map { entity ->
            genreEntityMapper.map(entity)
        })
    }
}