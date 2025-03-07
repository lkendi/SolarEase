package com.example.solarease.presentation.devices.panels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solarease.domain.usecases.GetSolarWeatherUseCase
import com.example.solarease.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class WeatherViewModel @Inject constructor(
    private val getSolarWeather: GetSolarWeatherUseCase
) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    fun loadWeatherData(lat: Double, lon: Double) {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading
            when (val result = getSolarWeather(lat, lon)) {
                is Resource.Success -> {
                    Log.d("WeatherViewModel", "loadWeatherData: ${result.data}")
                    _weatherState.value = WeatherState.Success(result.data)
                }
                is Resource.Error -> {
                    Log.d("WeatherViewModel", "loadWeatherData: ${result.message}")
                    _weatherState.value = WeatherState.Error(result.message)
                }
                is Resource.Loading -> {
                    Log.d("WeatherViewModel", "loadWeatherData: Loading")
                    _weatherState.value = WeatherState.Loading
                }
            }
        }
    }
}
