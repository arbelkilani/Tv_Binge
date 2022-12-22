package com.arbelkilani.binge.tv.data.source.remote

import com.arbelkilani.binge.tv.common.data.model.CertificationsResponse
import com.arbelkilani.binge.tv.common.data.model.ConfigurationResponse
import com.arbelkilani.binge.tv.common.data.model.ProvidersResponse
import com.arbelkilani.binge.tv.common.data.model.GenreResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val WATCH_REGION = "watch_region"
    }

    // Get API Configuration
    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse

    // Get Tv Providers
    @GET("watch/providers/tv")
    suspend fun getProviders(@Query(WATCH_REGION) watchRegion: String): ProvidersResponse

    // Get Tv Certifications
    @GET("certification/tv/list")
    suspend fun getCertifications(): CertificationsResponse

    // Get Tv List Genre
    @GET("genre/tv/list")
    suspend fun getGenres(): GenreResponse
}