package com.example.data.local.table.category

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CategoryTable: Table("Categories") {
    val id: Column<Long> = long("id").autoIncrement()
    val name: Column<String> = varchar("name", length = 100).uniqueIndex()
    val description : Column<String> = varchar("description", length = 151)
    val isVisible: Column<Boolean> = bool("isVisible")
    val imageUrl : Column<String> = varchar("imageUrl", length = 200)

    override val primaryKey: PrimaryKey?= PrimaryKey(id)
}