package com.arbelkilani.binge.tv.common.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arbelkilani.binge.tv.common.domain.entity.CertificationEntity
import com.arbelkilani.binge.tv.common.domain.entity.ConfigurationEntity
import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.common.domain.entity.WatchProviderEntity

@Database(
    entities = [ConfigurationEntity::class,
        WatchProviderEntity::class,
        CertificationEntity::class,
        GenreEntity::class],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun resourcesDao(): ResourcesDao
}