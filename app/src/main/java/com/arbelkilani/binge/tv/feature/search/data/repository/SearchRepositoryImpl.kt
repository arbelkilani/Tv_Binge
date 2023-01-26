package com.arbelkilani.binge.tv.feature.search.data.repository

import com.arbelkilani.binge.tv.common.data.mapper.GenreResponseMapper
import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.common.presentation.model.Genre
import com.arbelkilani.binge.tv.common.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.common.source.remote.ApiService
import com.arbelkilani.binge.tv.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val service: ApiService,
    database: AppDatabase
) : SearchRepository {

    @Inject
    lateinit var genreResponseMapper: GenreResponseMapper

    private val resourcesDao = database.resourcesDao()

    override suspend fun getGenres(): Flow<List<GenreEntity>> {
        val local = resourcesDao.getGenres()
        return if (local.isNullOrEmpty()) {
            flow {
                emit(service.getGenres().list.map {
                    genreResponseMapper.map(it, false)
                })
            }
        } else {
            flow { emit(local) }
        }
    }
}