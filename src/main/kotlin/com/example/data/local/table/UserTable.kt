package com.example.data.local.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UserTable : Table("Users") {
    val id: Column<Long> = long("id").autoIncrement()
    val username: Column<String> = varchar("username", length = 51)
    val email: Column<String> = varchar("email", length = 51)
    val password: Column<String> = varchar("password", length = 51)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}