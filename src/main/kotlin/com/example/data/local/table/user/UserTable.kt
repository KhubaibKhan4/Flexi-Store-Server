package com.example.data.local.table.user

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UserTable : Table("Users") {
    val id: Column<Long> = long("id").autoIncrement()
    val username: Column<String> = varchar("username", length = 51)
    val email: Column<String> = varchar("email", length = 51)
    val password: Column<String> = varchar("password", length = 1000)
    val fullName: Column<String> = varchar("fullName", length = 151)
    val address: Column<String> = varchar("address", length = 150)
    val city: Column<String> = varchar("city", length = 75)
    val country: Column<String> = varchar("country", length = 100)
    val postalCode: Column<Long> = long("postal_code")
    val phoneNumber: Column<String> = varchar("phoneNumber", length = 130)
    val userRole: Column<String> = varchar("userRole", length = 51)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}