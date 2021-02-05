package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherDescription(

    @SerializedName("main")
    val description: String,
    val icon: String
)