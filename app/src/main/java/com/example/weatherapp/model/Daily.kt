package com.example.weatherapp.model

import androidx.room.TypeConverters
import com.example.weatherapp.model.datasource.Converters
import com.google.gson.annotations.SerializedName

@TypeConverters(Converters::class)
data class Daily(

    @SerializedName("dt")
    val time: Long,
    val temp: Temp,
    @SerializedName("weather")
    val description: List<WeatherDescription>
)

data class Temp(

    @SerializedName("day")
    val dayTemp: Float,
    @SerializedName("min")
    val minTemp: Float,
    @SerializedName("max")
    val maxTemp: Float
)