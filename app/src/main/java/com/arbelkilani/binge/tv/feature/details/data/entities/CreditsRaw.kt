package com.arbelkilani.binge.tv.feature.details.data.entities

import com.google.gson.annotations.SerializedName

data class CreditsRaw(
    @SerializedName("cast") val cast: List<CreditsResponse>,
    @SerializedName("crew") val crew: List<CreditsResponse>,
    @SerializedName("id") val id: Int
)

data class CreditsResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("gender") val gender: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("character") val character: String,
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("order") val order: Int
)
