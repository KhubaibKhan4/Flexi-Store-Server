package com.example.domain.model.category

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Long,
    val name: String,
    val description: String,
    val isVisible: Boolean,
    val imageUrl: String,
)
