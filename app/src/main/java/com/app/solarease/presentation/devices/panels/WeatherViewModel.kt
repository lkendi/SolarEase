package com.app.solarease.presentation.devices.panels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.solarease.common.Resource
import com.app.solarease.domain.model.Weather
import com.app.solarease.domain.usecase.weather.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeather: GetWeatherUseCase
) : ViewModel() {
    private val _weatherState = MutableStateFlow<Resource<Weather>>(Resource.Loading())
    val weatherState: StateFlow<Resource<Weather>> = _weatherState

    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            val timezone = TimeZone.getDefault().id
            _weatherState.value = Resource.Loading()
            when (val result = getWeather(lat, lon, timezone)) {
                is Resource.Success -> _weatherState.value = result
                is Resource.Error   -> _weatherState.value = result
                else                -> {}
            }
        }
    }
}
