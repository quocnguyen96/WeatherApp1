package com.example.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.Daily
import com.example.weatherapp.utils.DATE
import com.example.weatherapp.utils.toDegree
import com.example.weatherapp.utils.toLocalTime
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_forecast_list_item.view.*

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {
    private var forecastList: List<Daily> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.view_forecast_list_item, parent, false)
        return ForecastViewHolder(inflater)
    }

    override fun getItemCount(): Int = forecastList.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(forecastList[position])
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(forecast: Daily) {
            itemView.tv_temp_min.text = forecast.temp.minTemp.toDegree()
            itemView.tv_temp_max.text = forecast.temp.maxTemp.toDegree()
            itemView.tv_date.text = forecast.time.toLocalTime(DATE)
            val iconId = forecast.description[0].icon
            val imageUrl = "http://openweathermap.org/img/wn/${iconId}@4x.png"
            Picasso.get().load(imageUrl).error(R.drawable.ic_cloud).into(itemView.imv_description)
        }
    }

    fun updateForecastList(forecastList: List<Daily>) {
        this.forecastList = forecastList
        notifyDataSetChanged()
    }
}