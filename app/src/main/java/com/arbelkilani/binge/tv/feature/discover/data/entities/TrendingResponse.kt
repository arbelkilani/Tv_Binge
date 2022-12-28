package com.arbelkilani.binge.tv.feature.discover.data.entities

import com.google.gson.annotations.SerializedName

data class TrendingResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)