package com.arbelkilani.binge.tv.feature.discover.domain.usecase

import androidx.paging.cachedIn
import androidx.paging.map
import com.arbelkilani.binge.tv.feature.discover.domain.mapper.TvEntityMapper
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import com.arbelkilani.binge.tv.feature.onboarding.domain.usecase.GetGenresUseCase
import com.arbelkilani.binge.tv.feature.onboarding.domain.usecase.GetProvidersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetDiscoverDataUseCase @Inject constructor(
    private val trendingUseCase: GetTrendingUseCase,
    private val startingThisMonthUseCase: GetStartingThisMonthUseCase,
    private val basedOnProvidersUseCase: GetBasedOnProvidersUseCase,
    private val freeUseCase: GetFreeUseCase,
    private val getProvidersUseCase: GetProvidersUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getBasedOnGenresUseCase: GetBasedOnGenresUseCase,
    private val getUpcomingUseCase: GetUpcomingUseCase
) {

    @Inject
    lateinit var mapper: TvEntityMapper

    suspend fun invoke(scope: CoroutineScope): Flow<DiscoverViewState.Data> {
        return flow {

        }
    }
}