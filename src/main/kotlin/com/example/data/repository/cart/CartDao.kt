package com.example.data.repository.cart

import com.example.domain.model.cart.CartItem

interface CartDao {
    suspend fun insert(
        productId: Long,
        quantity: Int,
        userId: Long
    ): CartItem?
    suspend fun getAllCart(): List<CartItem>?
    suspend fun getCartByUserId(id: Long): List<CartItem>?
    suspend fun getCartItemByUserId(id: Long): CartItem?
    suspend fun getCartItemByCartId(id: Int): CartItem?
    suspend fun deleteCartItemByCartId(id:Int): Int?
    suspend fun deleteCartByUserId(id:Long): Int?
    suspend fun update(
        cartId: Int,
        productId: Long,
        quantity: Int,
        userId: Long
    )
    suspend fun updateCartItem(
        cartId: Int,
        productId: Long,
        quantity: Int,
        userId: Long
    )
}