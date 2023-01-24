package com.arbelkilani.binge.tv.feature.details.data.entities

import com.google.gson.annotations.SerializedName

data class ExternalIdResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("imdb_id") val imdb: String?,
    @SerializedName("freebase_mid") val freebaseMid: String?,
    @SerializedName("freebase_id") val freebaseId: String?,
    @SerializedName("tvdb_id") val tvdb: Int?,
    @SerializedName("tvrage_id") val tvRage: String?,
    @SerializedName("wikidata_id") val wikidata: String?,
    @SerializedName("facebook_id") val facebook: String?,
    @SerializedName("instagram_id") val instagram: String?,
    @SerializedName("twitter_id") val twitter: String?
)