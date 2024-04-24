package com.example.data.repository.cart

import com.example.domain.model.cart.CartItem

interface CartDao {
    suspend fun insert(
        productId: Long,
        quantity: Int,
        product: Long
    ): CartItem?
    suspend fun getAllCart(): List<CartItem>?
    suspend fun getCartById(id: Long): CartItem?
    suspend fun deleteCartById(id:Long): Int?
    suspend fun update(
        productId: Long,
        quantity: Int,
        product: Long
    )
}