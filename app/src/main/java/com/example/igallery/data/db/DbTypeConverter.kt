package com.example.igallery.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type

class DbTypeConverter : Serializable {

    @TypeConverter
    fun fromString(value: String): MutableList<String> {
        val listType: Type = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: MutableList<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }


    @TypeConverter
    fun folderFromList(list: MutableList<Folder>): String {
        val gson = Gson()
        return gson.toJson(list)
    }


    @TypeConverter
    fun folderFromString(value: String): MutableList<Folder> {
        val listType: Type = object : TypeToken<MutableList<Folder>>() {}.type
        return Gson().fromJson(value, listType)
    }


    @TypeConverter
    fun imageFromList(list: MutableList<Image>): String {
        val gson = Gson()
        return gson.toJson(list)
    }


    @TypeConverter
    fun imageFromString(value: String): MutableList<Image> {
        val listType: Type = object : TypeToken<MutableList<Image>>() {}.type
        return Gson().fromJson(value, listType)
    }

}
