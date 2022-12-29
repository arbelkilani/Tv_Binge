package com.arbelkilani.binge.tv.common.source.local.room.converters

import androidx.room.TypeConverter
import com.arbelkilani.binge.tv.common.domain.model.Image
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