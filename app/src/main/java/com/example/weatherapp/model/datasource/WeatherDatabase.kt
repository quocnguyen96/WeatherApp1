package com.example.weatherapp.model.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.model.Daily
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherInfo

@Database(entities = [Weather::class], version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        private var INSTANCE: WeatherDatabase? = null
        fun getInstance(context: Context) : WeatherDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_db"
                ).build()
                INSTANCE = instance
                instance // return instance
            }

        }
    }
}
