package com.app.solarease.domain.usecase.location

import com.app.solarease.common.Resource
import com.app.solarease.domain.model.LatLng
import com.app.solarease.domain.repository.LocationRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Resource<LatLng> {
        return locationRepository.getLocation()
    }
}
