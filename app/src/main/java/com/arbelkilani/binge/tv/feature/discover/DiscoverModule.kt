package com.arbelkilani.binge.tv.feature.discover

import com.arbelkilani.binge.tv.feature.discover.data.repository.DiscoverRepositoryImpl
import com.arbelkilani.binge.tv.feature.discover.domain.repository.DiscoverRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module(includes = [BindModule::class])
object SearchFilteredModule {

    @Singleton
    @Provides
    fun provideNavigator(): DiscoverContract.ViewNavigation {
        return DiscoverNavigator()
    }
}

@InstallIn(SingletonComponent::class)
@Module
abstract class BindModule {
    @Binds
    abstract fun bindRepository(
        discoverRepository: DiscoverRepositoryImpl
    ): DiscoverRepository
}