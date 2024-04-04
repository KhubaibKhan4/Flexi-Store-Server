package com.example.plugins

import com.example.domain.reppository.category.CategoryRepository
import com.example.domain.reppository.user.UsersRepository
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
        val fullName = parameters["fullName"] ?: return@post call.respondText(
            text = "fullName Missing",
            status = HttpStatusCode.Unauthorized
        )
        val address = parameters["address"] ?: return@post call.respondText(
            text = "Address Missing",
            status = HttpStatusCode.Unauthorized
        )
        val city = parameters["city"] ?: return@post call.respondText(
            text = "City Missing",
            status = HttpStatusCode.Unauthorized
        )
        val country = parameters["country"] ?: return@post call.respondText(
            text = "Country Missing",
            status = HttpStatusCode.Unauthorized
        )
        val phoneNumber = parameters["phoneNumber"] ?: return@post call.respondText(
            text = "phoneNumber Missing",
            status = HttpStatusCode.Unauthorized
        )
        val userRole = parameters["userRole"] ?: return@post call.respondText(
            text = "User Role Missing",
            status = HttpStatusCode.Unauthorized
        )
        try {
            val users = db.insert(userName, email, password, fullName, address, city, country, phoneNumber, userRole)
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
    get("v1/users/{id}") {
        val parameters = call.parameters["id"]
        try {
            val usersId = parameters?.toLong()
            if (usersId == null) {
                return@get call.respondText(
                    "Invalid ID",
                    status = HttpStatusCode.BadRequest
                )
            }
            val user = db.getUserById(usersId)
            if (user == null) {
                return@get call.respondText(
                    text = "User Not Found",
                    status = HttpStatusCode.NotFound
                )
            } else {
                return@get call.respond(
                    HttpStatusCode.OK,
                    user
                )
            }

        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.Unauthorized,
                "Error While Fetching Data from Server : ${e.message}"
            )
        }

    }
    delete("v1/users/{id}") {
        val parameters = call.parameters["id"]
        try {
            val users = parameters?.toLongOrNull()?.let { usersId ->
                db.deleteUserById(usersId)
            } ?: return@delete call.respondText(
                text = "No Id Found",
                status = HttpStatusCode.BadRequest
            )
            if (users == 1) {
                call.respondText(
                    text = "Deleted Successfully",
                    status = HttpStatusCode.OK
                )
            } else {
                call.respondText(
                    text = "Id Not Found",
                    status = HttpStatusCode.BadRequest
                )
            }
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.Unauthorized,
                "Error While Deleting User From Server ${e.message}"
            )
        }
    }
    put("v1/users/{id}") {
        val id = call.parameters["id"] ?: return@put call.respondText(
            text = "Id Not Found",
            status = HttpStatusCode.NotFound
        )
        val parameters = call.receive<Parameters>()
        val username = parameters["username"] ?: return@put call.respondText(
            text = "Username Missing",
            status = HttpStatusCode.BadRequest
        )
        val email = parameters["email"] ?: return@put call.respondText(
            text = "email Missing",
            status = HttpStatusCode.BadRequest
        )
        val password = parameters["password"] ?: return@put call.respondText(
            text = "password Missing",
            status = HttpStatusCode.BadRequest
        )
        val fullName = parameters["fullName"] ?: return@put call.respondText(
            text = "fullName Missing",
            status = HttpStatusCode.BadRequest
        )
        val address = parameters["address"] ?: return@put call.respondText(
            text = "fullName Missing",
            status = HttpStatusCode.BadRequest
        )
        val city = parameters["city"] ?: return@put call.respondText(
            text = "fullName Missing",
            status = HttpStatusCode.BadRequest
        )
        val country = parameters["country"] ?: return@put call.respondText(
            text = "fullName Missing",
            status = HttpStatusCode.BadRequest
        )
        val phoneNumber = parameters["phoneNumber"] ?: return@put call.respondText(
            text = "fullName Missing",
            status = HttpStatusCode.BadRequest
        )
        try {
            val result = id.toLong().let { userId ->
                db.updateUsers(userId, username, email, password, fullName, address, city, country, phoneNumber)
            }
            if (result == 1) {
                call.respondText(
                    text = "Update Successfully...",
                    status = HttpStatusCode.OK
                )
            } else {
                call.respondText(
                    "Something Went Wrong...",
                    status = HttpStatusCode.BadRequest
                )
            }

        } catch (e: Exception) {
            call.respondText(
                text = e.message.toString(),
                status = HttpStatusCode.BadRequest
            )
        }
    }

}

fun Route.category(
    db: CategoryRepository
) {
    post("v1/categories") {
        val parameters = call.receive<Parameters>()
        val name = parameters["name"] ?: return@post call.respondText(
            text = "Name Missing",
            status = HttpStatusCode.BadRequest
        )
        val description = parameters["description"] ?: return@post call.respondText(
            text = "Description Missing",
            status = HttpStatusCode.BadRequest
        )
        val isVisible = parameters["isVisible"] ?: return@post call.respondText(
            text = "isVisible Missing",
            status = HttpStatusCode.BadRequest
        )
        val imageUrl = parameters["imageUrl"] ?: return@post call.respondText(
            text = "ImageUrl Missing",
            status = HttpStatusCode.BadRequest
        )

        try {
            val category = db.insert(name, description, isVisible.toBoolean(), imageUrl)
            category?.id.let { categoryId ->
                call.respond(
                    status = HttpStatusCode.OK,
                    "Category Data Uploaded Successfully $category"
                )
            }

        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.Unauthorized,
                "Error While Uploading Category To Server : ${e.message}"
            )
        }
    }
    get("v1/categories") {
        try {
            val category = db.getAllCategories()
            if (category == null) {
                call.respond(
                    HttpStatusCode.OK,
                    "No Users Found"
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    category
                )
            }
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.Unauthorized,
                "Error While Fetching  Data from Server ${e.message}"
            )
        }
    }
    get ("v1/categories/{id}"){
        val id = call.parameters["id"] ?: return@get call.respond(
            HttpStatusCode.BadRequest,
            "Id is Missing"
        )
        try {
            val categoryId = id.toLong()
            if (categoryId == null){
                call.respond(
                    HttpStatusCode.Unauthorized,
                    "Category Id Invalid"
                )
            }
            val categories = db.getCategoryById(categoryId)
            if (categories== null){
                call.respond(
                    HttpStatusCode.BadRequest,
                    "No Category Found..."
                )
            }else{
                call.respond(
                    HttpStatusCode.OK,
                    categories
                )
            }


        }catch (e: Exception){
            call.respond(
                HttpStatusCode.Unauthorized,
                "Error While Fetching Categories ${e.message}"
            )
        }
    }
}