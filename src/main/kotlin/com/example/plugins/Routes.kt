package com.example.plugins

import com.example.domain.model.Users
import com.example.domain.reppository.UsersRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.users(
    db: UsersRepository
) {
    post("v1/users") {
        val parameters = call.receive<Parameters>()
        val userName = parameters["username"] ?: return@post call.respondText(
            text = "Username Missing",
            status = HttpStatusCode.Unauthorized
        )
        val email = parameters["email"] ?: return@post call.respondText(
            text = "Email MISSING",
            status = HttpStatusCode.Unauthorized
        )
        val password = parameters["password"] ?: return@post call.respondText(
            text = "Password Missing",
            status = HttpStatusCode.Unauthorized
        )
        try {
            val users = db.insert(userName, email, password)
            users?.id.let {
                call.respond(
                    status = HttpStatusCode.OK,
                    "Uploaded to Server Successfully $users"
                )
            }
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.Unauthorized, "Error While Upload Data to Server ${e.message}")
        }
    }
    get("v1/users") {
        try {
            val users = db.getAllUsers()
            if (users?.isNotEmpty() == true) {
                call.respond(HttpStatusCode.OK, users)
            }
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.Unauthorized,
                "Error While Fetching Data From Server: ${e.message}"
            )
        }
    }

}