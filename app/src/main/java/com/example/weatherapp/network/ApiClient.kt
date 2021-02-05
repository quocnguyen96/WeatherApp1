package com.example.weatherapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val BASE_URL = "https://api.openweathermap.org"
    private const val REQUEST_TIME_OUT = 5

    val instance: ApiService = Retrofit.Builder().run {
        addConverterFactory(GsonConverterFactory.create())
        addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        baseUrl(BASE_URL)
        client(createInterceptorClient())
        build()
    }.create(ApiService::class.java)

    private fun createInterceptorClient():OkHttpClient {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .build()
    }
}

