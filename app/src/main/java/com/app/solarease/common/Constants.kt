package com.app.solarease.common

object Constants {
    const val WEATHER_BASE_URL      = "https://api.open-meteo.com/v1/"
    const val WEATHER_HOURLY_PARAMS = "shortwave_radiation,cloud_cover,temperature_2m,precipitation_probability"
    const val WEATHER_DAILY_PARAMS  = "sunrise,sunset,shortwave_radiation_sum,precipitation_probability_max,sunshine_duration"
    const val IOT_BASE_URL = "https://iotfunctionapp001.azurewebsites.net/"
}
