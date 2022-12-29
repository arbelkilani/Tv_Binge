package com.arbelkilani.binge.tv.common.source.remote

import com.arbelkilani.binge.tv.common.base.entities.ResponseWrapper
import com.arbelkilani.binge.tv.common.data.model.CertificationsResponse
import com.arbelkilani.binge.tv.common.data.model.ConfigurationResponse
import com.arbelkilani.binge.tv.common.data.model.ProvidersResponse
import com.arbelkilani.binge.tv.common.data.model.GenreResponse
import com.arbelkilani.binge.tv.feature.discover.data.entities.TvResponse
import retrofit2.http.GET
import retrofit2.http.Path
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

    // Get trending tv
    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrending(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String
    ): ResponseWrapper<TvResponse>
}