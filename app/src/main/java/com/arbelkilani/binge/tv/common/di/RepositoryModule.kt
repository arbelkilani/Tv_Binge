package com.arbelkilani.binge.tv.common.di

import com.arbelkilani.binge.tv.common.data.repository.*
import com.arbelkilani.binge.tv.common.domain.repository.*
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
    abstract fun bindResourcesRepository(
        resourcesRepositoryImpl: ResourcesRepositoryImpl
    ): ResourcesRepository

    @Binds
    abstract fun bindGenreRepository(
        genreRepositoryImpl: GenreRepositoryImpl
    ): GenreRepository

    @Binds
    abstract fun bindNetworkRepository(
        repositoryImpl: NetworkRepositoryImpl
    ): NetworkRepository

    @Binds
    abstract fun binProvidersRepository(
        repositoryImpl: ProviderRepositoryImpl
    ): ProviderRepository
}