package com.example.data.local.table.cart

import com.example.data.local.table.user.UserTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CartTable: Table("Cart") {
    val cartId: Column<Int> = integer("cartId").autoIncrement()
    val productId: Column<Long> = long("productId")
    val quantity: Column<Int> = integer("quantity")
    val userId: Column<Long> = long("userId").references(UserTable.id)
    override val primaryKey: PrimaryKey = PrimaryKey(cartId)
}