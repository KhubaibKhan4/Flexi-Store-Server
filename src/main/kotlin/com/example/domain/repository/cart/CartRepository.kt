package com.example.domain.repository.cart

import com.example.data.local.table.cart.CartTable
import com.example.data.local.table.db.DatabaseFactory
import com.example.data.repository.cart.CartDao
import com.example.domain.model.cart.CartItem
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
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
        return DatabaseFactory.dbQuery {
            CartTable.selectAll()
                .mapNotNull {
                    rowToResult(it)
                }
        }
    }

    override suspend fun getCartByUserId(id: Long): List<CartItem>? {
        return DatabaseFactory.dbQuery {
            CartTable.select(CartTable.userId.eq(id))
                .mapNotNull {
                rowToResult(it)
            }
        }
    }

    override suspend fun deleteCartByUserId(id: Long): Int? {
        return DatabaseFactory.dbQuery {
            CartTable.deleteWhere { CartTable.userId.eq(id) }
        }
    }

    override suspend fun update(productId: Long, quantity: Int, userId: Long) {
       return DatabaseFactory.dbQuery {
           CartTable.update({(CartTable.productId.eq(productId)) and (CartTable.userId eq userId)}){cart ->
               cart[CartTable.productId] = productId
               cart[CartTable.quality]= quantity
               cart[CartTable.userId] = userId
           }
       }
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