package com.arbelkilani.binge.tv.data.source.local.room.converters

import androidx.room.TypeConverter
import com.arbelkilani.binge.tv.domain.entities.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImageConverter {
    @TypeConverter
    fun from(string: String): Image {
        val type = object : TypeToken<Image>() {}.type
        return Gson().fromJson(string, type)
    }

    @TypeConverter
    fun to(types: Image): String {
        return Gson().toJson(types)
    }
}