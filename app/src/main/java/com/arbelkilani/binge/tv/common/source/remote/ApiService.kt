package com.arbelkilani.binge.tv.common.source.remote

import com.arbelkilani.binge.tv.common.base.entities.ResponseWrapper
import com.arbelkilani.binge.tv.common.data.response.CertificationsResponse
import com.arbelkilani.binge.tv.common.data.response.ConfigurationResponse
import com.arbelkilani.binge.tv.common.data.response.GenreResponse
import com.arbelkilani.binge.tv.common.data.response.ProvidersResponse
import com.arbelkilani.binge.tv.feature.details.data.entities.CreditsRaw
import com.arbelkilani.binge.tv.feature.details.data.entities.KeywordsRaw
import com.arbelkilani.binge.tv.feature.details.data.entities.TvDetailsResponse
import com.arbelkilani.binge.tv.feature.discover.data.response.PersonResponse
import com.arbelkilani.binge.tv.feature.discover.data.response.TvResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

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

    // Get trending person
    @GET("trending/person/{time_window}")
    suspend fun getTrendingPerson(
        @Path("time_window") timeWindow: String
    ) : ResponseWrapper<PersonResponse>

    // Get TV Airing Today
    @GET("tv/airing_today")
    suspend fun getAiringToday(
        @Query("page") page: Int,
        @Query("timezone") timezone: String
    ): ResponseWrapper<TvResponse>

    // Discover
    @GET("discover/tv")
    suspend fun discover(
        @Query("page") page: Int,
        @QueryMap options: Map<String, String?>
    ): ResponseWrapper<TvResponse>

    //
    @GET("tv/{tv_id}")
    suspend fun getTvDetails(
        @Path("tv_id") id: Int,
        @Query("append_to_response") appendToResponse: String
    ): TvDetailsResponse

    // Get Keywords
    @GET("tv/{tv_id}/keywords")
    suspend fun getKeywords(
        @Path("tv_id") id: Int
    ): KeywordsRaw

    // Get Credits
    @GET("tv/{tv_id}/credits")
    suspend fun getCredits(
        @Path("tv_id") id: Int
    ): CreditsRaw
}