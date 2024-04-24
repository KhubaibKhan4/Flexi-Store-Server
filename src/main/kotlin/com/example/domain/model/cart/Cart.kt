package com.example.domain.model.cart

import com.example.domain.model.product.Product
import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val productId: Long,
    val quantity: Int,
    val product: Product
)
