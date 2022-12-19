package com.arbelkilani.binge.tv.presentation.di

import android.content.Context
import androidx.room.Room
import com.arbelkilani.binge.tv.data.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.data.source.local.room.ConfigurationDao
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
    fun provideDao(database: AppDatabase): ConfigurationDao {
        return database.configurationDao()
    }
}