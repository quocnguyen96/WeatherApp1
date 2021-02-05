package com.example.weatherapp.utils

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun Long.toLocalTime(type: Int): String {
    val date = Date(this * 1000L)
    val dateFormat = when (type) {
        DATE_TIME -> SimpleDateFormat("dd-MM-yyyy HH:mm")
        DATE -> SimpleDateFormat("dd-MM")
        TIME ->SimpleDateFormat("HH:mm")
        else ->SimpleDateFormat("dd-MM-yyyy HH:mm")
    }
    dateFormat.timeZone = TimeZone.getTimeZone("GMT+7")
    return dateFormat.format(date)
}

fun Float.toDegree() = this.roundToInt().toString() + "\u00B0"

fun getAssetJsonData(context: Context): String? {
    val json: String?
    json = try {
        val inputStream: InputStream = context.assets.open("vn.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charset.forName("UTF-8"))
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return json
}