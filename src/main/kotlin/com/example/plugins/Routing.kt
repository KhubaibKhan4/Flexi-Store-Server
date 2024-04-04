package com.example.plugins

import com.example.data.local.table.db.DatabaseFactory
import com.example.domain.reppository.category.CategoryRepository
import com.example.domain.reppository.user.UsersRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    DatabaseFactory.init()
    val db = UsersRepository()
    val categoriesDb = CategoryRepository()
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        users(db)
        category(categoriesDb)
    }
}
