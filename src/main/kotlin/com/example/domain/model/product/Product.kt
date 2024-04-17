package com.example.domain.model.product

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val price: Long,
    val categoryId: Long,
    val categoryTitle: String,
    val imageUrl: String,
    val created_at: String,
    val updated_at: String,
    val total_stack: Long,
    val brand: String,
    val weight: Double,
    val dimensions: String,
    val isAvailable: Boolean,
    val discountPrice: Long,
    val promotionDescription: String,
    val averageRating: Double,
    val isFeatured: Boolean,
    val manufacturer: String,
    val colors: String,
)
