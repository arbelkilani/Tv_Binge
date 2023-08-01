package com.arbelkilani.binge.tv.common.domain.usecase

import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.common.domain.repository.GenreRepository
import javax.inject.Inject

class GetGenresByIdsUseCase @Inject constructor() {

    @Inject
    lateinit var genreRepository: GenreRepository

    suspend fun invoke(ids: List<String>): List<GenreEntity> {
        return genreRepository.getGenresByIds(ids)
    }
}