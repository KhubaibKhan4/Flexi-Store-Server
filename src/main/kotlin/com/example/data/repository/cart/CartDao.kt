package com.example.data.repository.cart

import com.example.domain.model.cart.CartItem

interface CartDao {
    suspend fun insert(
        productId: Long,
        quantity: Int,
        userId: Long
    ): CartItem?
    suspend fun getAllCart(): List<CartItem>?
    suspend fun getCartByUserId(id: Long): CartItem?
    suspend fun deleteCartByUserId(id:Long): Int?
    suspend fun update(
        productId: Long,
        quantity: Int,
        userId: Long
    )
}