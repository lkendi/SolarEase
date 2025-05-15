package com.app.solarease.domain.repository

import com.app.solarease.common.Resource
import com.app.solarease.domain.model.LatLng

interface LocationRepository {
    suspend fun getLocation(): Resource<LatLng>
    suspend fun checkLocationPermission(): Resource<Boolean>
}
