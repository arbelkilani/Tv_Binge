package com.arbelkilani.binge.tv.feature.search

import com.arbelkilani.binge.tv.feature.search.data.repository.SearchRepositoryImpl
import com.arbelkilani.binge.tv.feature.search.domain.repository.SearchRepository
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
    fun provideNavigator(): SearchContract.ViewNavigation {
        return SearchNavigator()
    }
}

@InstallIn(SingletonComponent::class)
@Module
abstract class BindModule {
    @Binds
    abstract fun bindRepository(
        searchRepository: SearchRepositoryImpl
    ): SearchRepository
}