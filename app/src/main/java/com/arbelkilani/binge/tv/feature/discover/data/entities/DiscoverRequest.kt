package com.arbelkilani.binge.tv.feature.discover.data.entities

import com.google.gson.annotations.SerializedName

data class DiscoverRequest(
    @SerializedName("air_date.gte") val airDateGte: String? = "",
    @SerializedName("air_date.lte") val airDateLte: String? = "",
    @SerializedName("watch_region") val watchRegion: String? = "",
    @SerializedName("with_watch_providers") val withWatchProviders: String? = ""
)