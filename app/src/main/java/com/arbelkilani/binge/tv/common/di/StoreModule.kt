package com.arbelkilani.binge.tv.common.di

import com.arbelkilani.binge.tv.data.source.local.prefsstore.PrefsStore
import com.arbelkilani.binge.tv.data.source.local.prefsstore.PrefsStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StoreModule {
    @Binds
    abstract fun bindPrefsStore(prefsStoreImpl: PrefsStoreImpl): PrefsStore
}