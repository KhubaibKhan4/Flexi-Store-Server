package com.example.data.repository.product

import com.example.domain.model.product.Product

interface ProductDao {
    suspend fun insert(
        name: String,
        description: String,
        price: Long,
        categoryId: Long,
        categoryTitle: String,
        imageUrl: String,
        created_at: String,
        updated_at: String,
        total_stack: Long,
        brand: String,
        weight: Double,
        dimensions: String,
        isAvailable: Boolean,
        discountPrice: Long,
        promotionDescription: String,
        averageRating: Double,
    ): Product?
}