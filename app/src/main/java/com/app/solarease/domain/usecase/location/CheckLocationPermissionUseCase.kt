package com.app.solarease.domain.usecase.location

import com.app.solarease.common.Resource
import com.app.solarease.domain.repository.LocationRepository
import javax.inject.Inject

class CheckLocationPermissionUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(): Resource<Boolean> {
        return repository.checkLocationPermission()
    }
}
