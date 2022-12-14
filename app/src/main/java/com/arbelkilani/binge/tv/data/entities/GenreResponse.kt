package com.arbelkilani.binge.tv.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres") val list: List<Genre>
)

@Entity
data class Genre(
    @SerializedName("id") val id: Int, @SerializedName("name") val name: String
)