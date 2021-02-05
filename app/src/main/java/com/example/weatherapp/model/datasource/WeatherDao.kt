package com.example.weatherapp.model.datasource

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherapp.model.Weather
import io.reactivex.Completable

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: Weather): Completable

    @Query("SELECT * FROM weather WHERE lat == :lat AND lon == :lng")
    fun getWeatherWithLatLong(lat: String, lng: String): LiveData<Weather>

    @Query("DELETE FROM weather")
    fun delete(): Completable

    @Update
    fun update(weather: Weather): Completable
}