package com.arbelkilani.binge.tv.feature.discover.data.entities

import com.google.gson.annotations.SerializedName

data class PersonResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("gender") val gender: Int,
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("known_for") val knownFor: List<KnownForResponse>
)

data class KnownForResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("genres_ids") val genresIds: List<Int>,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int
)
