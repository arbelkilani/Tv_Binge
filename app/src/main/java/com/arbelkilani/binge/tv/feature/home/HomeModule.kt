package com.arbelkilani.binge.tv.feature.home

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HomeModule {

    @Singleton
    @Provides
    fun provideNavigator(): HomeContract.ViewNavigation {
        return HomeNavigator()
    }
}