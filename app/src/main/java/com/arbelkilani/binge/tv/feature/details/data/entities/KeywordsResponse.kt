package com.arbelkilani.binge.tv.feature.details.data.entities

import com.google.gson.annotations.SerializedName

data class KeywordsRaw(
    @SerializedName("id") val id: String,
    @SerializedName("results") val results: List<KeywordsResponse>
)

data class KeywordsResponse(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int
)