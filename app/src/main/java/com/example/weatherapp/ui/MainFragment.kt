package com.example.weatherapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherInfo
import com.example.weatherapp.model.city.City
import com.example.weatherapp.model.city.CityItem
import com.example.weatherapp.ui.adapter.ForecastAdapter
import com.example.weatherapp.utils.DATE_TIME
import com.example.weatherapp.utils.getAssetJsonData
import com.example.weatherapp.utils.toLocalTime
import com.example.weatherapp.viewmodel.MainViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_main.*
import kotlin.math.roundToInt


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: ForecastAdapter
    private lateinit var selectedCity: CityItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = ViewModelProvider(this@MainFragment).get(MainViewModel::class.java)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpSpinner()
        setUpRecyclerView()
        setUpObserver()

        rfl_parent.setOnRefreshListener {
            rfl_parent.isRefreshing = false
            fetchData(selectedCity.lat, selectedCity.lng)
        }
    }

    private fun setUpSpinner() {
        val cityJson = getAssetJsonData(requireContext())
        val cityType = object : TypeToken<City>() {}.type
        val city: City = Gson().fromJson(cityJson, cityType)

        val adapter = ArrayAdapter<CityItem>(requireContext(), R.layout.spinner_item, city)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_city.adapter = adapter
        spn_city.onItemSelectedListener = object : OnItemSelectedListener {

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCity = adapterView?.getItemAtPosition(position) as CityItem
                val lat = selectedCity.lat
                val lng = selectedCity.lng
                fetchData(lat, lng)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun fetchData(lat: String, lng: String) {
        binding.viewModel?.getWeatherData(requireContext(), lat, lng)
    }

    private fun setUpRecyclerView() {
        adapter = ForecastAdapter()
        rcv_forecast.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcv_forecast.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.HORIZONTAL))
        rcv_forecast.adapter = adapter
    }

    private fun setUpObserver() {
        binding.viewModel?.error?.observe(viewLifecycleOwner, Observer {
            if (it) { // Show error dialog
                AlertDialog.Builder(activity).apply {
                    setTitle("Error")
                    setMessage("Can't get real time weather data!")
                    setPositiveButton("Dismiss") { dialog, _ -> dialog.dismiss() }
                    setNegativeButton("Retry") { _, _ -> fetchData(selectedCity.lat, selectedCity.lng) }
                    setCancelable(false)
                    show()
                }
            }
        })
        binding.viewModel?.mediator?.observe(viewLifecycleOwner, Observer {  })
        binding.viewModel?.weatherData?.observe(viewLifecycleOwner, Observer {
            binding.viewModel?.isEmpty?.value = (it == null)
            it?.let {
                processDataToView(it.current)
                adapter.updateForecastList(it.daily)
            }
            tv_temp.setOnClickListener { _ ->
                val hourlyList = Gson().toJson(it.hourly)
                val bundle = bundleOf("hourly_list" to hourlyList)
                findNavController().navigate(R.id.action_mainFragment_to_hourlyFragment, bundle)
            }
        })
    }

    private fun processDataToView(current: WeatherInfo) {
        binding.current = current
        tv_temp.text = getString(R.string.temp, current.temp.roundToInt())
        tv_real_feel.text = getString(R.string.feel_like, current.realFeel.roundToInt())
        tv_date_time.text = current.time.toLocalTime(DATE_TIME)
        val iconId = current.description[0].icon
        val imageUrl = "http://openweathermap.org/img/wn/${iconId}@4x.png"
        Picasso.get().load(imageUrl).error(R.drawable.ic_cloud).into(imv_weather)
    }
}