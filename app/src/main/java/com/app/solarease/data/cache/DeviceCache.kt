package com.app.solarease.data.cache

import com.app.solarease.domain.model.Device
import javax.inject.Singleton

@Singleton
class DeviceCache {
    private var cachedDevices: List<Device> = emptyList()
    private var lastUpdated: Long = 0

    fun saveDevices(devices: List<Device>) {
        cachedDevices = devices
        lastUpdated = System.currentTimeMillis()
    }

    fun getDevices(): List<Device> {
        return if (System.currentTimeMillis() - lastUpdated < CACHE_DURATION) {
            cachedDevices
        } else {
            emptyList()
        }
    }

    fun isValid(): Boolean {
        return System.currentTimeMillis() - lastUpdated < CACHE_DURATION
    }

    companion object {
        private const val CACHE_DURATION = 60 * 60 * 1000
    }
}
