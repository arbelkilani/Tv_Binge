package com.arbelkilani.binge.tv.feature.walkthrough

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WalkthroughModule {

    @Singleton
    @Provides
    fun provideNavigator(): WalkthroughContract.ViewNavigation {
        return WalkthroughNavigator()
    }
}