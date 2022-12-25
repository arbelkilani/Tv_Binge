package com.arbelkilani.binge.tv.common.di

import android.content.Context
import androidx.room.Room
import com.arbelkilani.binge.tv.common.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.common.source.local.room.ResourcesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "com.arbelkilani.binge.tv.database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideResourcesDao(database: AppDatabase): ResourcesDao {
        return database.resourcesDao()
    }
}