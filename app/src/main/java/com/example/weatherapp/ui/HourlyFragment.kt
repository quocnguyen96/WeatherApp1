package com.example.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.model.WeatherInfo
import com.example.weatherapp.ui.adapter.HourlyAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_hourly.*

class HourlyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hourly, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hourlyListJson = arguments?.getString("hourly_list")
        val hourlyListType = object : TypeToken<List<WeatherInfo>>() {}.type
        val hourlyList: List<WeatherInfo> = Gson().fromJson(hourlyListJson, hourlyListType)
        setupRecyclerView(hourlyList)
    }

    private fun setupRecyclerView(hourlyList: List<WeatherInfo>) {
        rcv_hourly.adapter = HourlyAdapter(hourlyList.subList(0, 24))
        rcv_hourly.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcv_hourly.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
    }
}