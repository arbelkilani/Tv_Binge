package com.arbelkilani.binge.tv.common.data.repository

import com.arbelkilani.binge.tv.common.data.mapper.GenreResponseMapper
import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.common.domain.repository.GenreRepository
import com.arbelkilani.binge.tv.common.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.common.source.remote.ApiService
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val service: ApiService, database: AppDatabase
) : GenreRepository {

    @Inject
    lateinit var genreResponseMapper: GenreResponseMapper

    private val resourcesDao = database.resourcesDao()

    override suspend fun getGenresByIds(ids: List<String>): List<GenreEntity> {
        val local = resourcesDao.getGenres()
        return if (local.isNullOrEmpty()) {
            service.getGenres().list.map { response ->
                val entity = genreResponseMapper.map(response = response, isFavorite = false)
                resourcesDao.saveGenre(entity)
                entity
            }.filter { it.id.toString() in ids }
        } else {
            local.filter { it.id.toString() in ids }
        }
    }
    
    override suspend fun getGenres(): List<GenreEntity> {
        val local = resourcesDao.getGenres()
        return if (local.isNullOrEmpty()) {
            service.getGenres().list.map { response ->
                val entity = genreResponseMapper.map(response = response, isFavorite = false)
                resourcesDao.saveGenre(entity)
                entity
            }
        } else {
            local
        }
    }
}