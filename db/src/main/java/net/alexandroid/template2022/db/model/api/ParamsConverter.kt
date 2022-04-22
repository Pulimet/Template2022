package net.alexandroid.template2022.db.model.api

import androidx.room.TypeConverter
import com.google.gson.Gson

class ParamsConverter {
    private val gson = Gson()

    @TypeConverter
    fun listToJsonString(value: List<Param>?): String = gson.toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = gson.fromJson(value, Array<Param>::class.java).toList()
}