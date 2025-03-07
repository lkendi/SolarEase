package com.example.solarease.domain.model

data class Recommendation(
    val id: String,
    val message: String,
    val timestamp: Long,
    val relatedParameter: String? = null,
    val confidence: Double? = null
)
