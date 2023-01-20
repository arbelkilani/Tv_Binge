package com.arbelkilani.binge.tv.common.data.response

import com.google.gson.annotations.SerializedName

data class ProvidersResponse(
    @SerializedName("results") val providers: List<Provider>
)

data class Provider(
    @SerializedName("display_priorities") val displayPriorities:Map<String, Int>,
    @SerializedName("display_priority") val displayPriority: Int,
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("provider_name") val providerName: String,
    @SerializedName("provider_id") val providerId: Int
)
