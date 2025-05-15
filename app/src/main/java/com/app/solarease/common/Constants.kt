package com.app.solarease.common

object Constants {
    const val BASE_URL      = "https://api.open-meteo.com/v1/"
    const val HOURLY_PARAMS = "shortwave_radiation,cloud_cover,temperature_2m,precipitation_probability"
    const val DAILY_PARAMS  = "sunrise,sunset,shortwave_radiation_sum,precipitation_probability_max,sunshine_duration"
}
