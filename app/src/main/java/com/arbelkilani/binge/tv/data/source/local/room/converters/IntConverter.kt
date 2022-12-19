package com.arbelkilani.binge.tv.data.source.local.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IntConverter {
    @TypeConverter
    fun fromString(string: String): List<Int> {
        val type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(string, type)
    }

    @TypeConverter
    fun fromList(types: List<Int>): String {
        return Gson().toJson(types)
    }
}