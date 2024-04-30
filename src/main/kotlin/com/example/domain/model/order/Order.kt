package com.example.domain.model.order

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: Long,
    val userId: Int,
    val productIds: Int,
    val totalQuantity: String,
    val totalPrice: Int,
    val orderProgress: String,
    val selectedColor: String,
    val paymentType: String,
    val trackingId: String,
    val orderDate: String,
    val deliveryDate: String,
)
