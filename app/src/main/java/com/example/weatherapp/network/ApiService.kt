package com.example.weatherapp.network


import com.example.weatherapp.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "019196fb4af032a27b4be150ce1fef32"
const val UNIT = "metric"
const val LAT = 10.762622
const val LONG = 106.66017

interface ApiService {

    @GET("/data/2.5/onecall")
    fun getWeatherInfo(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") unit: String = UNIT,
        @Query("appid") apiKey: String = API_KEY
    ): Call<Weather>
}