package com.example.data.local.table.cart

import com.example.data.local.table.product.ProductTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CartTable: Table("Cart") {
    val productId: Column<Long> = long("productId")
    val quality: Column<Int> = integer("quantity")
    val product: Column<Long> = long("product").references(ProductTable.id)
}