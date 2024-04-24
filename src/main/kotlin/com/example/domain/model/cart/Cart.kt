package com.example.domain.model.cart

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val productId: Long,
    val quantity: Int,
    val userId: Long
)
