package com.arbelkilani.binge.tv.data.entities

import com.google.gson.annotations.SerializedName

data class ProvidersResponse(
    @SerializedName("results") val providers: List<Provider>
)

data class Provider(
    @SerializedName("display_priority") val displayPriority: Int,
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("provider_name") val providerName: String,
    @SerializedName("provider_id") val providerId: Int
)
