package com.app.solarease.domain.usecase.device

import com.app.solarease.common.Resource
import com.app.solarease.domain.model.Device
import com.app.solarease.domain.repository.DeviceRepository
import javax.inject.Inject

class GetDevicesUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(): Resource<List<Device>> {
        return deviceRepository.getDevices()
    }
}
