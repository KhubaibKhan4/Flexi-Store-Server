package com.example.domain.repository.order

import com.example.data.local.table.db.DatabaseFactory
import com.example.data.local.table.order.OrderTable
import com.example.data.repository.order.OrderDao
import com.example.domain.model.order.Order
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement

class OrderRepository : OrderDao {
    override suspend fun insert(
        userId: Int,
        productIds: Int,
        totalQuantity: String,
        totalPrice: Int,
        orderProgress: String,
        selectedColor: String,
        paymentType: String,
        trackingId: String,
        orderDate: String,
        deliveryDate: String
    ): Order? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = OrderTable.insert { order ->
                order[OrderTable.userId] = userId
                order[OrderTable.productIds] = productIds
                order[OrderTable.totalQuantity] = totalQuantity
                order[OrderTable.totalPrice] = totalPrice
                order[OrderTable.orderProgress] = orderProgress
                order[OrderTable.selectedColor] = selectedColor
                order[OrderTable.paymentType] = paymentType
                order[OrderTable.trackingId] = trackingId
                order[OrderTable.orderDate] = orderDate
                order[OrderTable.deliveryDate] =deliveryDate
            }

        }
        return rowToResult(statement?.resultedValues?.get(0)!!)
    }

    override suspend fun getAllOrders(): List<Order> {
        return DatabaseFactory.dbQuery {
            OrderTable.selectAll().mapNotNull { rowToResult(it) }
        }
    }

    override suspend fun getAllOrdersByUserId(id: Int): List<Order> {
        return DatabaseFactory.dbQuery {
            OrderTable.select { OrderTable.userId eq id }
                .mapNotNull { rowToResult(it) }
        }
    }

    override suspend fun getOrderById(id: Long): Order? {
        return DatabaseFactory.dbQuery {
            OrderTable.select { OrderTable.id eq id }
                .map { rowToResult(it) }
                .singleOrNull()
        }
    }

    override suspend fun updateOrderProgress(id: Long, orderProgress: String, currentTime: String): Int {
        return DatabaseFactory.dbQuery {
            OrderTable.update({ OrderTable.id eq id }) { order ->
                order[OrderTable.orderProgress] = orderProgress
                order[OrderTable.deliveryDate] = currentTime
            }
        }
    }

    override suspend fun deleteOrderById(id: Long): Int {
        return DatabaseFactory.dbQuery {
            OrderTable.deleteWhere { OrderTable.id eq id }
        }
    }
}

private fun rowToResult(row: ResultRow): Order? {
    if (row == null) {
        return null
    } else {
        return Order(
            id = row[OrderTable.id],
            userId = row[OrderTable.userId],
            productIds = row[OrderTable.productIds],
            totalQuantity = row[OrderTable.totalQuantity],
            totalPrice = row[OrderTable.totalPrice],
            orderProgress = row[OrderTable.orderProgress],
            selectedColor = row[OrderTable.selectedColor],
            paymentType = row[OrderTable.paymentType],
            trackingId = row[OrderTable.trackingId],
            orderDate = row[OrderTable.orderDate],
            deliveryDate = row[OrderTable.deliveryDate],
        )
    }
}