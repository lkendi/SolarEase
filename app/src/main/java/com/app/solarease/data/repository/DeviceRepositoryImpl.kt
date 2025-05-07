package com.app.solarease.data.repository

import com.app.solarease.common.Resource
import com.app.solarease.data.model.DeviceDto
import com.app.solarease.data.mapper.toDomain
import com.app.solarease.data.remote.FirestoreService
import com.app.solarease.domain.model.Device
import com.app.solarease.domain.repository.DeviceRepository
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val firestoreService: FirestoreService
) : DeviceRepository {

    override suspend fun getDevices(): Resource<List<Device>> {
        return try {
            val dtos = firestoreService.getDocuments(
                collectionPath = "devices",
                parser = ::parseDeviceDocument
            )
            Resource.Success(dtos.map { it.toDomain() })
        } catch (e: Exception) {
            Resource.Error("Device fetch failed: ${e.message}")
        }
    }

    override suspend fun getDeviceDetails(id: String): Resource<Device> {
        return firestoreService.getDocumentById(
            collectionPath = "devices",
            documentId = id,
            parser = ::parseDeviceDocument
        )?.toDomain()?.let { Resource.Success(it) }
            ?: Resource.Error("Device $id not found")
    }

    private fun parseDeviceDocument(doc: DocumentSnapshot): DeviceDto? {
        return when (doc.getString("device_type")) {
            "inverter" -> doc.toObject(DeviceDto.InverterDto::class.java)
            "battery" -> doc.toObject(DeviceDto.BatteryDto::class.java)
            "panel" -> doc.toObject(DeviceDto.PanelDto::class.java)
            else -> null
        }
    }
}
