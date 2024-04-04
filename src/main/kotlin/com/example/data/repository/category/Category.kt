package com.example.data.repository.category

interface Category {
    suspend fun insert(
        name: String,
        description: String,
        isVisible: Boolean,
        imageUrl: String
    ): Category?
    suspend fun getAllCategories(): List<Category>?
    suspend fun getCategoryById(id: Long): Category?
    suspend fun deleteCategoryById(id: Long): Int?
    suspend fun updateCategoryById(
        id: Long,
        name: String,
        description: String,
        isVisible: Boolean,
        imageUrl: String
    ):Int?
}