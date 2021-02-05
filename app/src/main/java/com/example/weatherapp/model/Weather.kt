package com.example.weatherapp.model

import androidx.room.Entity

@Entity(tableName = "weather", primaryKeys = ["lat", "lon"])
data class Weather(
    val lat: String,
    val lon: String,
    val current: WeatherInfo,
    val daily: List<Daily>,
    val hourly: List<WeatherInfo>
)