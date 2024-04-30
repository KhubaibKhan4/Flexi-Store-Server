package com.example.data.local.table.order

import org.jetbrains.exposed.sql.Table

object OrderTable : Table("Orders") {
    val id = long("id").autoIncrement()
    val userId = integer("userId")
    val productIds = integer("productIds")
    val totalQuantity = varchar("totalQuantity", length = 1000)
    val totalPrice = integer("totalPrice")
    val orderProgress = varchar("orderProgress", length = 500)
    val selectedColor= varchar("selectedColor",1000)
    val paymentType = varchar("paymentType", length = 500)
    val trackingId = varchar("trackingId", length = 5000)
    val orderDate= varchar("orderDate", length = 300)
    val deliveryDate= varchar("deliveryDate", length = 300)


    override val primaryKey = PrimaryKey(id)
}