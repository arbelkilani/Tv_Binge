package com.arbelkilani.binge.tv.common.data.response

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class GenreRaw(
    @SerializedName("genres") val list: List<GenreResponse>
)

@Entity
data class GenreResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)