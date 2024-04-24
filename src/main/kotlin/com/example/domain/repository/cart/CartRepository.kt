package com.example.domain.repository.cart

import com.example.data.repository.cart.CartDao
import com.example.domain.model.cart.CartItem

class CartRepository : CartDao {
    override suspend fun insert(productId: Long, quantity: Int, product: Long): CartItem? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCart(): List<CartItem>? {
        TODO("Not yet implemented")
    }

    override suspend fun getCartById(id: Long): CartItem? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCartById(id: Long): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun update(productId: Long, quantity: Int, product: Long) {
        TODO("Not yet implemented")
    }
}