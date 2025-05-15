package com.app.solarease.presentation.devices.panels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.solarease.common.Resource
import com.app.solarease.domain.model.LatLng
import com.app.solarease.domain.model.Weather
import com.app.solarease.domain.usecase.location.GetLocationUseCase
import com.app.solarease.domain.usecase.weather.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _weatherState = MutableStateFlow<Resource<Weather>>(Resource.Loading())
    val weatherState: StateFlow<Resource<Weather>> = _weatherState

    private val _cachedWeather = MutableStateFlow<Weather?>(null)
    val cachedWeather: StateFlow<Weather?> = _cachedWeather

    fun fetchWeather(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _weatherState.value = Resource.Loading()
            val locationResult = getLocationUseCase()
            if (locationResult is Resource.Success) {
                val latLng: LatLng = locationResult.data
                val timezone = TimeZone.getDefault().id
                val result = getWeatherUseCase(
                    latLng.latitude,
                    latLng.longitude,
                    timezone,
                    forceRefresh
                )
                when (result) {
                    is Resource.Success -> {
                        _cachedWeather.value = result.data
                        _weatherState.value = result
                    }
                    is Resource.Error -> {
                        val cached = _cachedWeather.value
                        if (cached != null) {
                            _weatherState.value = Resource.Success(cached)
                        } else {
                            _weatherState.value = Resource.Error(result.message)
                        }
                    }
                    else -> {
                        _weatherState.value = result
                    }
                }
            } else if (locationResult is Resource.Error) {
                _weatherState.value = Resource.Error(locationResult.message)
            }
        }
    }
}
