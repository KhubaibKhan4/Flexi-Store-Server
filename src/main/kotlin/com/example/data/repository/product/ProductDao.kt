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
        manufacturer: String,
        colors: String
    ): Product?

    suspend fun getAllProducts(): List<Product>?
    suspend fun getProductById(id: Long): Product?
    suspend fun deleteProductById(id: Long): Int?

    suspend fun updateProductById(
        id: Long,
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
        isFeature: Boolean,
        manufacturer: String,
        colors: String
    ): Int?
}
