package com.arbelkilani.binge.tv.feature.onboarding.domain.usecase

import com.arbelkilani.binge.tv.common.domain.entities.GenreEntity
import com.arbelkilani.binge.tv.feature.onboarding.domain.repository.OnBoardingRepository
import javax.inject.Inject

class UpdateGenreUseCase @Inject constructor() {

    @Inject
    lateinit var onBoardingRepository: OnBoardingRepository

    suspend fun invoke(genre: GenreEntity) {
        onBoardingRepository.updateGenreState(genre)
    }
}