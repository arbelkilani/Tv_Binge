package com.arbelkilani.binge.tv.common.data.repository

import com.arbelkilani.binge.tv.common.data.mapper.GenreMapper
import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.common.domain.repository.GenreRepository
import com.arbelkilani.binge.tv.common.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.common.source.remote.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val service: ApiService, database: AppDatabase
) : GenreRepository {

    @Inject
    lateinit var genreMapper: GenreMapper

    private val resourcesDao = database.resourcesDao()

    override suspend fun getGenres() = flow {
        val localGenres = resourcesDao.getGenres()
        emit(if (localGenres.isNullOrEmpty()) {
            service.getGenres().list.map {
                genreMapper.map(it, isFavorite = false)
            }
        } else localGenres)
    }

    override suspend fun getGenreById(id: String): GenreEntity? {
        return resourcesDao.getGenreById(id)
            ?: service.getGenres().list.filter { it.id.toString() == id }
                .map { genreMapper.map(it, isFavorite = false) }.firstOrNull()
    }

    override suspend fun getGenresByIds(ids: List<String>): List<GenreEntity> {
        val localGenres = resourcesDao.getGenres()?.filter { it.id.toString() in ids }
        return if (localGenres.isNullOrEmpty()) {
            service.getGenres().list.filter { it.id.toString() in ids }.map {
                genreMapper.map(it, false)
            }
        } else localGenres
    }

    override suspend fun updateGenreState(id: String, isFavorite: Boolean) {
        resourcesDao.updateGenreState(id, isFavorite)
    }

    override suspend fun saveGenre(genreEntity: GenreEntity) {
        resourcesDao.saveGenre(genreEntity)
    }

    override suspend fun saveGenres(list: List<GenreEntity>) {
        resourcesDao.saveGenres(list)
    }
}