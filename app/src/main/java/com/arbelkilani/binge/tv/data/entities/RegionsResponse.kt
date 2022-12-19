package com.arbelkilani.binge.tv.data.entities

import com.google.gson.annotations.SerializedName

data class RegionsResponse(
    @SerializedName("results") val regions: List<Region>
)

data class Region(
    @SerializedName("iso_3166_1") val iso: String,
    @SerializedName("english_name") val englishName: String,
    @SerializedName("native_name") val nativeName: String
)