package com.app.solarease.data.repository

import com.app.solarease.common.Resource
import com.app.solarease.data.cache.DeviceCache
import com.app.solarease.data.remote.FirestoreService
import com.app.solarease.domain.model.Device
import com.app.solarease.domain.repository.DeviceRepository
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val firestore: FirestoreService,
    private val cache: DeviceCache
) : DeviceRepository {

    override suspend fun getDevices(forceRefresh: Boolean): Resource<List<Device>> {
        return try {
            if (!forceRefresh) {
                cache.getDevices().takeIf { it.isNotEmpty() && cache.isValid() }?.let {
                    return Resource.Success(it)
                }
            }

            val devices = firestore.getDocuments("devices") { doc: DocumentSnapshot ->
                doc.toObject(Device::class.java)
            }
            cache.saveDevices(devices)
            Resource.Success(devices)
        } catch (e: Exception) {
            cache.getDevices().takeIf { it.isNotEmpty() }?.let { Resource.Success(it) }
                ?: Resource.Error(e.message ?: "Device load failed")
        }
    }
}
