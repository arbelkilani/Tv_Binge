package com.arbelkilani.binge.tv.common.di

import com.arbelkilani.binge.tv.common.data.repository.ConfigurationRepositoryImpl
import com.arbelkilani.binge.tv.common.domain.repository.ConfigurationRepository
import com.arbelkilani.binge.tv.feature.walkthrough.data.repository.ResourcesRepositoryImpl
import com.arbelkilani.binge.tv.feature.walkthrough.domain.repository.ResourcesRepository
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
}