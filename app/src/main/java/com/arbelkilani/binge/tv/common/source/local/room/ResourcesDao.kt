package com.arbelkilani.binge.tv.common.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arbelkilani.binge.tv.common.domain.model.ApiConfigurationEntity
import com.arbelkilani.binge.tv.common.domain.model.CertificationEntity
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity

@Dao
interface ResourcesDao {

    // Watch Provider
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWatchProvider(entity: WatchProviderEntity)

    @Query("SELECT * FROM watch_provider_table")
    suspend fun getWatchLocalProviders(): List<WatchProviderEntity>?

    @Query("UPDATE watch_provider_table SET isFavorite=:state WHERE id=:id")
    suspend fun updateProviderState(id: Int, state: Boolean)

    @Query("SELECT * FROM watch_provider_table WHERE isFavorite=:state")
    suspend fun getFavoriteProviders(state: Boolean = true): List<WatchProviderEntity>?

    // Api Configuration
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveApiConfiguration(entity: ApiConfigurationEntity)

    @Query("SELECT * FROM configuration_table")
    suspend fun getApiConfiguration(): ApiConfigurationEntity?

    // Certifications
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCertification(entity: CertificationEntity)

    @Query("SELECT * FROM certification_table")
    suspend fun getCertifications(): List<CertificationEntity>?

    // Genres
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenres(list: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenre(entity: GenreEntity)

    @Query("SELECT * FROM genre_table")
    suspend fun getGenres(): List<GenreEntity>?

    @Query("UPDATE genre_table SET isFavorite=:favorite WHERE id=:id")
    suspend fun updateGenreState(id: String, favorite: Boolean)

    @Query("SELECT * FROM genre_table WHERE id=:id")
    fun getGenreById(id: String): GenreEntity?
}