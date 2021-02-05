package com.example.weatherapp.model.datasource

import androidx.room.TypeConverter
import com.example.weatherapp.model.Daily
import com.example.weatherapp.model.WeatherInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun currentToJson(current: WeatherInfo): String = Gson().toJson(current)

    @TypeConverter
    fun jsonToCurrent(json: String): WeatherInfo {
        val currentType = object : TypeToken<WeatherInfo>() {}.type
        return Gson().fromJson(json, currentType)
    }

    @TypeConverter
    fun dailyToJson(daily: List<Daily>): String = Gson().toJson(daily)

    @TypeConverter
    fun jsonToDaily(json: String): List<Daily> {
        val dailyType = object : TypeToken<List<Daily>>() {}.type
        return Gson().fromJson(json, dailyType)
    }

    @TypeConverter
    fun hourlyToJson(hourly: List<WeatherInfo>): String = Gson().toJson(hourly)

    @TypeConverter
    fun jsonToHourly(json: String): List<WeatherInfo> {
        val hourlyType = object : TypeToken<List<WeatherInfo>>() {}.type
        return Gson().fromJson(json, hourlyType)
    }
}