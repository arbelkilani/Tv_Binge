package com.arbelkilani.binge.tv.feature.onboarding

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object OnBoardingModule {

    @Singleton
    @Provides
    fun provideNavigator(): OnBoardingContract.ViewNavigation {
        return OnBoardingNavigator()
    }
}