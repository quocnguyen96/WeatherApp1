package com.example.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.model.Weather
import com.example.weatherapp.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val isFailed = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()
    var weatherData = MutableLiveData<Weather>()

    fun getWeatherData(lat: String, lng: String) {
        Repository.getInstance(getApplication()).getWeather(lat, lng) { isSuccess, response ->
            isFailed.value = !isSuccess
            if (isSuccess) {
                weatherData.value = response
            } else {
                Repository.getInstance(getApplication()).getWeatherDb(lat, lng)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        weatherData.value = it
                    }, {
                        isError.value = true
                    })
            }
        }
    }
}
