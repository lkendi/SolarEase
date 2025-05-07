package com.app.solarease.common


object Constants {
    const val BASE_URL      = "https://api.open-meteo.com/v1/"
    const val TIMEZONE      = "auto"
    const val HOURLY_PARAMS = "shortwave_radiation,cloud_cover,precipitation_probability"
    const val DAILY_PARAMS  = "sunrise,sunset,uv_index_max,precipitation_probability_max"
}
