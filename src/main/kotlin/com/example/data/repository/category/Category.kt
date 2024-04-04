package com.example.data.repository.category

interface Category {
    suspend fun insert(
        name: String,
        description: String,
        isVisible: Boolean,
        imageUrl: String
    ): Category?
}