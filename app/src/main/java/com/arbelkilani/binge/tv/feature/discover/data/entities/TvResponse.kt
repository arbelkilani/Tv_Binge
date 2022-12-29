package com.arbelkilani.binge.tv.feature.discover.data.entities

import com.google.gson.annotations.SerializedName

data class TvResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("adult") val isAdult: Boolean,
    @SerializedName("backdrop_path") val backdrop: String?,
    @SerializedName("original_language") val language: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster: String?,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("genre_ids") val genres: List<String>
)