package com.app.solarease.domain.model

data class User(
    val id: String,
    val email: String?,
    val photoUrl: String?,
    val displayName: String?,
    val location: LatLng? = null
)
