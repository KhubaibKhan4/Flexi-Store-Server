package com.example.plugins

import com.example.data.local.table.db.DatabaseFactory
import com.example.domain.repository.category.CategoryRepository
import com.example.domain.repository.product.ProductRepository
import com.example.domain.repository.user.UsersRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    DatabaseFactory.init()
    val db = UsersRepository()
    val categoriesDb = CategoryRepository()
    val productsDb = ProductRepository()
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        users(db)
        category(categoriesDb)
        products(productsDb)
    }
}
