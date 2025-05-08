package com.app.solarease.presentation.viewmodel

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
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {
    private val _weatherState = MutableStateFlow<Resource<Weather>>(Resource.Loading())
    val weatherState: StateFlow<Resource<Weather>> = _weatherState

    fun fetchWeather(lat: Double, lon: Double, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            val timezone = TimeZone.getDefault().id
            _weatherState.value = Resource.Loading()
            _weatherState.value = getWeatherUseCase(lat, lon, timezone, forceRefresh)
        }
    }
}
