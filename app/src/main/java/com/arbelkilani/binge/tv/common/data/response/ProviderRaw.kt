package com.arbelkilani.binge.tv.common.data.response

import com.google.gson.annotations.SerializedName

data class ProviderRaw(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val result: Map<String, ProviderMap>
)

data class ProviderMap(
    @SerializedName("link") val link: String,
    @SerializedName("flatrate") val flatrate: List<ProviderResponse>?,
    @SerializedName("buy") val buy: List<ProviderResponse>?,
    @SerializedName("rent") val rent: List<ProviderResponse>?,
    @SerializedName("free") val free: List<ProviderResponse>?
)

data class ProviderResponse(
    @SerializedName("logo_path") val logo: String,
    @SerializedName("provider_id") val providerId: Int,
    @SerializedName("provider_name") val providerName: String,
    @SerializedName("display_priority") val priority: Int
)
