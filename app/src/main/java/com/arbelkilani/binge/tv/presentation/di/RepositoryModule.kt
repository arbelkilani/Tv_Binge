package com.arbelkilani.binge.tv.presentation.di

import com.arbelkilani.binge.tv.data.repositories.ConfigurationRepositoryImpl
import com.arbelkilani.binge.tv.data.repositories.WatchProvidersRepositoryImpl
import com.arbelkilani.binge.tv.domain.repositories.ConfigurationRepository
import com.arbelkilani.binge.tv.domain.repositories.WatchProvidersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindConfigurationRepository(configurationRepositoryImpl: ConfigurationRepositoryImpl): ConfigurationRepository

    @Binds
    abstract fun bindWatchProvidersRepository(watchProvidersRepositoryImpl: WatchProvidersRepositoryImpl): WatchProvidersRepository
}