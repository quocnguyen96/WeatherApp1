package com.example.weatherapp.model

import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.example.weatherapp.model.datasource.Converters
import com.google.gson.annotations.SerializedName


@TypeConverters(Converters::class)
data class WeatherInfo(

    @SerializedName("dt")
    val time: Long,

    val temp: Float,

    @ColumnInfo(name = "real_feel")
    @SerializedName("feels_like")
    val realFeel: Float,

    val humidity: Int,

    @SerializedName("weather")
    val description: List<WeatherDescription>

)


