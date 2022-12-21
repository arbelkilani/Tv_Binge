package com.arbelkilani.binge.tv.presentation.splash

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SplashModule {

    @Singleton
    @Provides
    fun provideNavigator(): SplashContract.ViewNavigation = SplashNavigator()
}