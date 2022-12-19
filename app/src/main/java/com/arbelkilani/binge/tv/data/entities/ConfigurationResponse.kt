package com.arbelkilani.binge.tv.data.entities

import com.google.gson.annotations.SerializedName

data class ConfigurationResponse(
    @SerializedName("images") val imagesConfiguration: ImageConfiguration,
    @SerializedName("change_keys") val changeKeys: List<String>
)

data class ImageConfiguration(
    @SerializedName("base_url") val baseUrl: String,
    @SerializedName("secure_base_url") val secureBaseUrl: String,
    @SerializedName("backdrop_sizes") val backdropSize: List<Int>,
    @SerializedName("logo_sizes") val logoSize: List<Int>,
    @SerializedName("poster_sizes") val posterSize: List<Int>,
    @SerializedName("profile_sizes") val profileSize: List<Int>,
    @SerializedName("still_sizes") val stillSize: List<Int>
)