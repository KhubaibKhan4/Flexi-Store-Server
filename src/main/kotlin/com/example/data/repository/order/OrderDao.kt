package com.example.data.repository.order

import com.example.domain.model.order.Order

interface OrderDao {
    suspend fun insert(
        userId: Int,
        productIds: Int,
        totalQuantity: String,
        totalPrice: Int,
        orderProgress: String,
        selectedColor: String,
        paymentType: String,
        trackingId: String
    ): Order?

    suspend fun getAllOrdersByUserId(id:Int): List<Order>
    suspend fun getOrderById(id: Long): Order?

    suspend fun updateOrderProgress(id: Long, orderProgress: String): Int

    suspend fun deleteOrderById(id: Long): Int
}