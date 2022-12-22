package com.arbelkilani.binge.tv.domain.usecase

import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.domain.repositories.WatchProvidersRepository
import javax.inject.Inject

class SaveWatchProvidersUseCase @Inject constructor() {

    @Inject
    lateinit var watchProvidersRepository: WatchProvidersRepository

    fun execute(providers: MutableList<WatchProviderEntity>) {
        watchProvidersRepository.saveSelectedWatchProviders(providers)
    }
}