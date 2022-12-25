package com.arbelkilani.binge.tv.common.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arbelkilani.binge.tv.common.domain.model.ApiConfigurationEntity
import com.arbelkilani.binge.tv.common.domain.model.CertificationEntity
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.common.source.local.room.converters.ImageConverter

@Database(
    entities = [
        ApiConfigurationEntity::class,
        WatchProviderEntity::class,
        CertificationEntity::class,
        GenreEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ImageConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resourcesDao(): ResourcesDao
}