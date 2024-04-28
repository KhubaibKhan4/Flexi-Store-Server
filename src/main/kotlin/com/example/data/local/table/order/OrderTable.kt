package com.example.data.local.table.order

import org.jetbrains.exposed.sql.Table

object OrderTable : Table("Orders") {
    val id = long("id").autoIncrement()
    val userId = long("userId")
    val productIds = varchar("productIds", length = 1000)
    val totalQuantity = integer("totalQuantity")
    val totalPrice = double("totalPrice")
    val orderProgress = varchar("orderProgress", length = 500)
    val paymentType = varchar("paymentType", length = 500)
    val trackingId = varchar("trackingId", length = 5000)

    override val primaryKey = PrimaryKey(id)
}