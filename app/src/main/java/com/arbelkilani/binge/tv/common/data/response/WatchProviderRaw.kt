package com.arbelkilani.binge.tv.common.data.response

import com.google.gson.annotations.SerializedName

data class WatchProviderRaw(
    @SerializedName("results") val results: List<WatchProviderResponse>
)

data class WatchProviderResponse(
    @SerializedName("display_priorities") val displayPriorities: Map<String, Int>,
    @SerializedName("display_priority") val displayPriority: Int,
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("provider_name") val providerName: String,
    @SerializedName("provider_id") val providerId: Int
)
