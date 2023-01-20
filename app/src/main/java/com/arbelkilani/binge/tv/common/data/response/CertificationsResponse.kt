package com.arbelkilani.binge.tv.common.data.response

import com.google.gson.annotations.SerializedName

data class CertificationsResponse(
    @SerializedName("certifications") val certifications: Map<String, List<Certification>>
)

data class Certification(
    @SerializedName("certification") val name: String,
    @SerializedName("meaning") val meaning: String,
    @SerializedName("order") val order: Int
)