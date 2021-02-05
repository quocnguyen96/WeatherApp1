package com.example.weatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Weather
import com.example.weatherapp.repository.Repository

class MainViewModel : ViewModel() {

    val error = MutableLiveData<Boolean>()
    val isEmpty = MutableLiveData<Boolean>()
    var weatherData = MutableLiveData<Weather>()
    val mediator = MediatorLiveData<Weather>()

    fun getWeatherData(context: Context, lat: String, lng: String) {
        Repository.getInstance(context).getWeather(lat, lng) { isSuccess, response ->
            error.value = !isSuccess
            if (isSuccess) {
                weatherData.value = response
            } else {
                mediator.addSource(Repository.getInstance(context).getWeatherDb(lat, lng)) {
                    weatherData.value = it
                }
            }
        }
    }

    fun getDataFromDb(context: Context, lat: String, lng: String): LiveData<Weather> = Repository.getInstance(context).getWeatherDb(lat, lng)
}
