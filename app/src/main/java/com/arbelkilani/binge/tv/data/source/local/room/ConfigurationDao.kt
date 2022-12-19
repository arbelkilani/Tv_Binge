package com.arbelkilani.binge.tv.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.arbelkilani.binge.tv.domain.entities.ConfigurationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfigurationDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(configurationEntity: ConfigurationEntity)

    @Query("SELECT * FROM configuration_table")
    fun get(): Flow<ConfigurationEntity>
}