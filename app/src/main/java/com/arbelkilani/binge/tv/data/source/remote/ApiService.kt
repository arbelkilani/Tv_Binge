package com.arbelkilani.binge.tv.data.source.remote

import com.arbelkilani.binge.tv.data.entities.ConfigurationResponse
import com.arbelkilani.binge.tv.data.entities.GenreResponse
import com.arbelkilani.binge.tv.data.entities.ProvidersResponse
import com.arbelkilani.binge.tv.data.entities.RegionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val WATCH_REGION = "watch_region"
    }

    // Configuration
    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse

    @GET("genre/tv/list")
    suspend fun getGenres(): GenreResponse

    @GET("watch/providers/regions")
    suspend fun getProviderRegions(): RegionsResponse

    @GET("watch/providers/tv")
    suspend fun getProviders(@Query(WATCH_REGION) watchRegion: String): ProvidersResponse
}