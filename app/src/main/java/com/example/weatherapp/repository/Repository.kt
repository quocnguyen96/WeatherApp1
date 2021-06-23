package com.example.weatherapp.repository

import android.content.Context
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.datasource.WeatherDatabase
import com.example.weatherapp.network.ApiClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Repository(context: Context) {

    private val db = WeatherDatabase.getInstance(context)

    fun getWeather(lat: String, lng: String, onResult: (isSuccess: Boolean, response: Weather?) -> Unit) {
        ApiClient.instance.getWeatherInfo(lat, lng).enqueue(object : Callback<Weather> {
            override fun onFailure(call: Call<Weather>, t: Throwable) {
                onResult(false, null)
            }

            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.body() != null && response.isSuccessful) {
                    onResult(true, response.body())
                    processDb(response.body())
                } else {
                    onResult(false, null)
                }
            }
        })
    }

    fun processDb(weather: Weather?) {
        db.weatherDao().insertWeather(weather!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun getWeatherDb(lat: String, lng: String): Single<Weather> = db.weatherDao().getWeatherWithLatLong(lat, lng)

    companion object {
        private var INSTANCE: Repository? = null
        fun getInstance(context: Context) = INSTANCE ?: Repository(context).also {
            INSTANCE = it
        }
    }
}
