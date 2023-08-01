package com.arbelkilani.binge.tv.common.domain.usecase

import androidx.paging.PagingData
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
        emit(PagingData.from(genreRepository.getGenres().map { entity ->
            genreEntityMapper.map(entity)
        }))
    }
}