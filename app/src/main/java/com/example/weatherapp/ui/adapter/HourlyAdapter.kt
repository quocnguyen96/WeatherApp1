package com.example.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.WeatherInfo
import com.example.weatherapp.utils.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_forecast_list_item.view.*
import kotlinx.android.synthetic.main.view_hourly_list_item.view.*
import kotlinx.android.synthetic.main.view_hourly_list_item.view.imv_description
import kotlinx.android.synthetic.main.view_hourly_list_item.view.tv_temp

class HourlyAdapter(private val hourlyList: List<WeatherInfo>) :
    RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {

    class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hourly: WeatherInfo) {
            itemView.tv_temp.text = hourly.temp.toDegree()
            itemView.tv_time.text = hourly.time.toLocalTime(TIME)
            val iconId = hourly.description[0].icon
            val imageUrl = "http://openweathermap.org/img/wn/${iconId}@4x.png"
            Picasso.get().load(imageUrl).into(itemView.imv_description)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_hourly_list_item, parent, false)
        return HourlyViewHolder(inflater)
    }

    override fun getItemCount() = hourlyList.size

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bind(hourlyList[position])
    }
}