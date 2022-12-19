package com.arbelkilani.binge.tv.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arbelkilani.binge.tv.data.source.local.room.converters.IntConverter
import com.arbelkilani.binge.tv.domain.entities.ConfigurationEntity

@Database(
    entities = [ConfigurationEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(IntConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun configurationDao(): ConfigurationDao
}