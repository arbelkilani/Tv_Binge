package com.arbelkilani.binge.tv.feature.details

import com.arbelkilani.binge.tv.feature.details.data.repositories.TvDetailsRepositoryImpl
import com.arbelkilani.binge.tv.feature.details.domain.repositories.TvDetailsRepository
import com.arbelkilani.binge.tv.feature.onboarding.BindModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [BindModule::class])
object TvDetailsModule {

    @Singleton
    @Provides
    fun provideNavigator(): TvDetailsContract.ViewNavigation {
        return TvDetailsNavigator()
    }
}

@InstallIn(SingletonComponent::class)
@Module
abstract class BindModule {
    @Binds
    abstract fun bindRepository(
        tvDetailsRepository: TvDetailsRepositoryImpl
    ): TvDetailsRepository
}