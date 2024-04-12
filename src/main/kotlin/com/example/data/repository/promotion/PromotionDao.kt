package com.example.data.repository.promotion

import com.example.domain.model.promotion.Promotion

interface PromotionDao {
    suspend fun insert(
        title: String,
        description: String,
        imageUrl: String,
        startDate: Long,
        endDate: Long,
        enable: Boolean
    ): Promotion?
    suspend fun getPromotionsList(): List<Promotion>?
    suspend fun getPromotionById(id: Long): Promotion?
    suspend fun deletePromotionById(id: Long): Int?
    suspend fun updatePromotion(
        id: Long,
        title: String,
        description: String,
        imageUrl: String,
        startDate: Long,
        endDate: Long,
        enable: Boolean
    )
}