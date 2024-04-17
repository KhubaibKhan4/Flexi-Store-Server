package com.example.data.local.table.product

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ProductTable:Table("Product") {
    val id: Column<Long> = long("id").autoIncrement()
    val name: Column<String> = varchar("name", length = 100)
    val description: Column<String> = varchar("description", length = 1000)
    val price: Column<Long> = long("price")
    val categoryId: Column<Long> = long("category_id")
    val categoryTitle: Column<String> = varchar("category_title", length = 51)
    val imageUrl: Column<String> = varchar("imageUrl", length = 51)
    val created_at: Column<String> = varchar("created_at", length = 51)
    val updated_at: Column<String> = varchar("updated_at", length = 51)
    val total_stack: Column<Long> = long("total_stack")
    val brand: Column<String> = varchar("brand", length = 51)
    val weight: Column<Double> = double("weight")
    val dimensions: Column<String> = varchar("dimensions", length = 51)
    val isAvailable: Column<Boolean> = bool("isAvailable")
    val discountPrice: Column<Long> = long("discountPrice")
    val promotionDescription: Column<String> = varchar("promotionDescription", length = 151)
    val averageRating: Column<Double> = double("averageRating")
    val isFeature: Column<Boolean> = bool("isFeature")
    val manufacturer: Column<String> = varchar("manufacturer",450)
    val colors: Column<String> = varchar("colors", 1000)

    override val primaryKey: PrimaryKey? = PrimaryKey(id)
}