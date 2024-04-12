package com.example.domain.model.promotion

import kotlinx.serialization.Serializable

@Serializable
data class Promotion(
    val id: Long,
    val imageUrl: String,
    val title: String,
    val description: String,
    val startDate: Long,
    val endDate: Long,
    val enabled: Boolean
)
