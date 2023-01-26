package com.arbelkilani.binge.tv.feature.home

import com.arbelkilani.binge.tv.feature.home.data.repository.HomeRepositoryImpl
import com.arbelkilani.binge.tv.feature.home.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [BindModule::class])
object DiscoverModule {

    @Singleton
    @Provides
    fun provideNavigator(): HomeContract.ViewNavigation {
        return HomeNavigator()
    }
}

@InstallIn(SingletonComponent::class)
@Module
abstract class BindModule {
    @Binds
    abstract fun bindRepository(
        discoverRepository: HomeRepositoryImpl
    ): HomeRepository
}