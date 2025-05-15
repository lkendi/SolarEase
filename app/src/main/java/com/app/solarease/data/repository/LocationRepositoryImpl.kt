package com.app.solarease.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.app.solarease.common.Resource
import com.app.solarease.domain.model.LatLng
import com.app.solarease.domain.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val context: Context
) : LocationRepository {
    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun getLocation(): Resource<LatLng> {
        return try {
            val location = fusedLocationClient.lastLocation.await()
            if (location != null) {
                Resource.Success(LatLng(location.latitude, location.longitude))
            } else {
                Resource.Error("Location unavailable")
            }
        } catch (e: Exception) {
            Resource.Error("Error: ${e.message}")
        }
    }

    override suspend fun checkLocationPermission(): Resource<Boolean> {
        return try {
            val hasFineLocation = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            val hasCoarseLocation = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            Resource.Success(hasFineLocation || hasCoarseLocation)
        } catch (e: Exception) {
            Resource.Error("Permission check failed: ${e.message}")
        }
    }
}
