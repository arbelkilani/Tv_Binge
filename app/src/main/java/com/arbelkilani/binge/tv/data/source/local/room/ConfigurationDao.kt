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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(configurationEntity: ConfigurationEntity)

    @Query("SELECT * FROM configuration_table")
    suspend fun get(): ConfigurationEntity?
}