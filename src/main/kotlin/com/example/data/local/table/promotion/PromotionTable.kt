package com.example.data.local.table.promotion

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object PromotionTable: Table("promotions") {
    val id: Column<Long> = long("id").autoIncrement()
    val title: Column<String> = varchar("title",200)
    val description: Column<String>  = varchar("description", 300)
    val imageUrl: Column<String> = varchar("imageUrl",500)
    val startDate: Column<Long> = long("startDate")
    val endDate: Column<Long> = long("endDate")
    val enabled: Column<Boolean> = bool("enable")
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}