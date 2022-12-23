package com.arbelkilani.binge.tv.feature.onboarding

import com.arbelkilani.binge.tv.feature.onboarding.data.repository.OnBoardingRepositoryImpl
import com.arbelkilani.binge.tv.feature.onboarding.domain.repository.OnBoardingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [BindModule::class])
object OnBoardingModule {

    @Singleton
    @Provides
    fun provideNavigator(): OnBoardingContract.ViewNavigation {
        return OnBoardingNavigator()
    }
}

@InstallIn(SingletonComponent::class)
@Module
abstract class BindModule {
    @Binds
    abstract fun bindProviderSelectionRepository(
        providerSelectionRepository: OnBoardingRepositoryImpl
    ): OnBoardingRepository
}