package com.example.weatherapp.model.datasource

import androidx.room.*
import com.example.weatherapp.model.Weather
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: Weather): Completable

    @Query("SELECT * FROM weather WHERE lat == :lat AND lon == :lng")
    fun getWeatherWithLatLong(lat: String, lng: String): Single<Weather>

    @Query("DELETE FROM weather")
    fun delete(): Completable

    @Update
    fun update(weather: Weather): Completable
}