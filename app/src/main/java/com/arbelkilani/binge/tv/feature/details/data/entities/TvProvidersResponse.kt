package com.arbelkilani.binge.tv.feature.details.data.entities

import com.google.gson.annotations.SerializedName

data class TvProvidersResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val result: Map<String, TvProviderResult>
)

data class TvProviderResult(
    @SerializedName("link") val link: String,
    @SerializedName("flatrate") val flatrates: List<Flatrate>
)

data class Flatrate(
    @SerializedName("logo_path") val logo: String,
    @SerializedName("provider_id") val providerId: Int,
    @SerializedName("provider_name") val providerName: String,
    @SerializedName("display_priority") val priority: Int
)
