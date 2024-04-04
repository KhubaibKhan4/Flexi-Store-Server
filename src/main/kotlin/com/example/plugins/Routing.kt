package com.example.plugins

import com.example.data.local.table.db.DatabaseFactory
import com.example.domain.reppository.user.UsersRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    DatabaseFactory.init()
    val db = UsersRepository()
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        users(db)
    }
}
