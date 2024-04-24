package com.example.domain.repository.cart

import com.example.data.local.table.cart.CartTable
import com.example.data.repository.cart.CartDao
import com.example.domain.model.cart.CartItem
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class CartRepository : CartDao {
    override suspend fun insert(productId: Long, quantity: Int, userId: Long): CartItem? {
        return try {
            transaction {
                val statement = CartTable.insert { cart ->
                    cart[CartTable.productId] = productId
                    cart[CartTable.quality] = quantity
                    cart[CartTable.userId] = userId
                }
                val firstResult = statement.resultedValues?.firstOrNull()!!
                rowToResult(firstResult)
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getAllCart(): List<CartItem>? {
        TODO("Not yet implemented")
    }

    override suspend fun getCartByUserId(id: Long): CartItem? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCartByUserId(id: Long): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun update(productId: Long, quantity: Int, userId: Long) {
        TODO("Not yet implemented")
    }

    private fun rowToResult(row: ResultRow): CartItem? {
        if (row == null) {
            return null
        } else {
            return CartItem(
                productId = row[CartTable.productId],
                quantity = row[CartTable.quality],
                userId = row[CartTable.userId]
            )
        }
    }

}