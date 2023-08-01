package com.arbelkilani.binge.tv.feature.details.data.entities

import com.arbelkilani.binge.tv.common.data.response.GenreResponse
import com.google.gson.annotations.SerializedName

data class TvDetailsResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdrop: String?,
    @SerializedName("created_by") val createdBy: List<CreatedBy>,
    @SerializedName("episode_run_time") val episodeRunTime: List<Int>,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("genres") val genreResponses: List<GenreResponse>,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("id") val id: Int,
    @SerializedName("in_production") val inProduction: Boolean,
    @SerializedName("languages") val languages: List<String>,
    @SerializedName("last_air_date") val lastAirDate: String,
    @SerializedName("last_episode_to_air") val lastEpisodeToAir: EpisodeToAirResponse?,
    @SerializedName("name") val name: String,
    @SerializedName("next_episode_to_air") val nextEpisodeToAir: EpisodeToAirResponse?,
    @SerializedName("networks") val networks: List<NetworkResponse>,
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int,
    @SerializedName("origin_country") val originCountry: List<String>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("poster_path") val poster: String?,
    @SerializedName("production_companies") val productionCompanies: List<Company>,
    @SerializedName("production_countries") val productionCountries: List<Country>,
    @SerializedName("seasons") val seasons: List<Season>,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguage>,
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("type") val type: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("videos") val videosResponse: VideosResponse
)

data class CreatedBy(
    @SerializedName("id") val id: String,
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("profile_path") val profile: String?
)

data class EpisodeToAirResponse(
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode_number") val episodeNumber: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("production_code") val productionCode: String,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("season_number") val seasonNumber: Int,
    @SerializedName("show_id") val showId: Int,
    @SerializedName("still_path") val stillPath: String?,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int
)

data class NetworkResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("logo_path") val logo: String?,
    @SerializedName("origin_country") val originCountry: String
)

data class Company(
    @SerializedName("id") val id: Int,
    @SerializedName("logo") val logo: String?,
    @SerializedName("name") val name: String,
    @SerializedName("origin_country") val originCountry: String
)

data class Country(
    @SerializedName("iso_3166_1") val isoCode: String, @SerializedName("name") val name: String
)

data class Season(
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode_count") val episodeCount: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster: String?,
    @SerializedName("season_number") val seasonNumber: Int
)

data class SpokenLanguage(
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_639_1") val isoCode: String,
    @SerializedName("name") val name: String
)

data class VideosResponse(
    @SerializedName("results") val videoResult: List<VideoResult>
)

data class VideoResult(
    @SerializedName("iso_639_1") val iso6391: String,
    @SerializedName("iso_3166_1") val iso31661: String,
    @SerializedName("name") val name: String,
    @SerializedName("key") val key: String,
    @SerializedName("site") val site: String,
    @SerializedName("size") val size: Int,
    @SerializedName("type") val type: String,
    @SerializedName("official") val official: Boolean,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("id") val id: String
)