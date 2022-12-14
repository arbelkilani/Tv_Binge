package com.arbelkilani.binge.tv.data.source.remote

import com.arbelkilani.binge.tv.data.entities.GenreResponse
import retrofit2.http.GET

interface ApiService {

    @GET("genre/tv/list")
    suspend fun getGenres(): GenreResponse
}