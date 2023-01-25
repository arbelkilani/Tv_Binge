package com.arbelkilani.binge.tv.feature.details.data.entities

import com.google.gson.annotations.SerializedName

data class ContentRatingsRaw(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: List<ContentRatingsResponse>
)

data class ContentRatingsResponse(
    @SerializedName("iso_3166_1") val isoCode: String,
    @SerializedName("rating") val rating: String
)
