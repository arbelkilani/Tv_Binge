package com.arbelkilani.binge.tv.common.di

import android.content.Context
import com.arbelkilani.binge.tv.common.application.BingeTvApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provide(@ApplicationContext app: Context): BingeTvApp {
        return app as BingeTvApp
    }
}