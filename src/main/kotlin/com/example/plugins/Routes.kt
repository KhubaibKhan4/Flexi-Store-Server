package com.example.plugins

import com.example.domain.reppository.category.CategoryRepository
import com.example.domain.reppository.product.ProductRepository
import com.example.domain.reppository.user.UsersRepository
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

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
        val multipart = call.receiveMultipart()
        var name: String? = null
        var description: String? = null
        var isVisible: Boolean? = null
        var imageUrl: String? = null

        multipart.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    when (part.name) {
                        "name" -> name = part.value
                        "description" -> description = part.value
                        "isVisible" -> isVisible = part.value.toBoolean()
                    }
                }

                is PartData.FileItem -> {
                    val fileBytes = part.streamProvider().readBytes()
                    val fileName = part.originalFileName ?: "uploaded_image_${System.currentTimeMillis()}"
                    val directoryPath = File("/var/www/uploads")
                    if (!directoryPath.exists()) {
                        directoryPath.mkdirs()
                    }
                    val filePath = "$directoryPath/$fileName"
                    File(filePath).writeBytes(fileBytes)
                    imageUrl = "/uploads/${fileName.replace(" ", "_")}"
                }

                else -> {}
            }
            part.dispose()
        }

        name ?: return@post call.respond(
            status = HttpStatusCode.BadRequest,
            "Name Missing"
        )
        description ?: return@post call.respond(
            status = HttpStatusCode.BadRequest,
            "Description Missing"
        )
        isVisible ?: return@post call.respond(
            status = HttpStatusCode.BadRequest,
            "isVisible Missing"
        )
        imageUrl ?: return@post call.respond(
            status = HttpStatusCode.BadRequest,
            "ImageUrl Missing"
        )

        try {
            val category = db.insert(name!!, description!!, isVisible!!, imageUrl!!)
            category?.id.let { categoryId ->
                call.respond(
                    status = HttpStatusCode.OK,
                    "Category Uploaded Successfully to Server : $category"
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
            if (category?.isNotEmpty() == true) {
                call.respond(HttpStatusCode.OK, category)
            } else {
                call.respond(HttpStatusCode.BadRequest, "No Categories Found...")
            }

        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.Unauthorized,
                "Error While Fetching  Data from Server ${e.message}"
            )
        }
    }
    get("v1/categories/{id}") {
        val id = call.parameters["id"] ?: return@get call.respond(
            HttpStatusCode.BadRequest,
            "Id is Missing"
        )
        try {
            val categoryId = id.toLong()
            if (categoryId == null) {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    "Category Id Invalid"
                )
            }
            val categories = db.getCategoryById(categoryId)
            if (categories == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "No Category Found..."
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    categories
                )
            }


        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.Unauthorized,
                "Error While Fetching Categories ${e.message}"
            )
        }
    }
    delete("v1/categories/{id}") {
        val id = call.parameters["id"] ?: return@delete call.respond(
            HttpStatusCode.BadRequest,
            "Id Missing"
        )
        try {
            val categories = db.deleteCategoryById(id.toLong())
            if (categories == 1) {
                call.respond(HttpStatusCode.OK, "Category Deleted Successfully $categories")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Id Not Found...")
            }
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.Unauthorized,
                "Error While Fetching Categories.. ${e.message}"
            )
        }
    }

    put("v1/categories/{id}") {
        val id = call.parameters["id"]?.toLong() ?: return@put call.respondText(
            text = "Invalid Id",
            status = HttpStatusCode.BadRequest
        )

        val multipart = call.receiveMultipart()
        var name: String? = null
        var description: String? = null
        var isVisible: Boolean? = null
        var imageUrl: String? = null

        multipart.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    when (part.name) {
                        "name" -> name = part.value
                        "description" -> description = part.value
                        "isVisible" -> isVisible = part.value.toBoolean()
                    }
                }

                is PartData.FileItem -> {
                    val fileBytes = part.streamProvider().readBytes()
                    val fileName = part.originalFileName ?: "uploaded_image_${System.currentTimeMillis()}"
                    val directoryPath = Paths.get("/var/www/uploads/") // Adjust the directory path as needed

                    // Create the directory if it doesn't exist
                    if (!Files.exists(directoryPath)) {
                        Files.createDirectories(directoryPath)
                    }

                    val filePath = "$directoryPath/$fileName"
                    Files.write(Paths.get(filePath), fileBytes)
                    imageUrl = "/uploads/categories/${fileName.replace(" ", "_")}"
                }

                is PartData.BinaryChannelItem -> TODO()
                is PartData.BinaryItem -> TODO()
            }
            part.dispose()
        }

        name ?: return@put call.respond(
            status = HttpStatusCode.BadRequest,
            "Name Missing"
        )
        description ?: return@put call.respond(
            status = HttpStatusCode.BadRequest,
            "Description Missing"
        )
        isVisible ?: return@put call.respond(
            status = HttpStatusCode.BadRequest,
            "isVisible Missing"
        )
        imageUrl ?: return@put call.respond(
            status = HttpStatusCode.BadRequest,
            "ImageUrl Missing"
        )

        try {
            val result = db.updateCategoryById(id, name!!, description!!, isVisible!!, imageUrl!!)
            if (result == 1) {
                call.respond(HttpStatusCode.OK, "Update Successfully")
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Something Went Wrong..."
                )
            }

        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.Unauthorized,
                "Error While Updating Data to Server : ${e.message}"
            )
        }
    }
}

fun Route.products(
    db: ProductRepository
) {
    post("v1/products") {
        val multipart = call.receiveMultipart()
        var name: String? = null
        var description: String? = null
        var price: Long? = null
        var categoryId: Long? = null
        var categoryTitle: String? = null
        var imageUrl: String? = null
        var created_at: String? = null
        var updated_at: String? = null
        var total_stack: Long? = null
        var brand: String? = null
        var weight: Double? = null
        var dimensions: String? = null
        var isAvailable: Boolean? = null
        var discountPrice: Long? = null
        var promotionDescription: String? = null
        var averageRating: Double? = null
        val uploadDir = File("upload/products/")
        if (uploadDir.exists()){
            uploadDir.mkdirs()
    }
        multipart.forEachPart { partData ->
            when (partData) {
                is PartData.FileItem -> {
                    val fileName = partData.originalFileName?.replace(" ", "_") ?: "image${System.currentTimeMillis()}"
                    val file = File(uploadDir, fileName)
                    partData.streamProvider().use { input ->
                        file.outputStream().buffered().use { output ->
                            input.copyTo(output)
                        }

                    }
                    imageUrl = "/upload/products/${fileName}"
                }

                is PartData.FormItem -> {
                    when (partData.name) {
                        "name" -> name = partData.value
                        "description" -> description = partData.value
                        "price" -> price = partData.value.toLong()
                        "categoryId" -> categoryId = partData.value.toLongOrNull()
                        "categoryTitle" -> categoryTitle = partData.value
                        "created_at" -> created_at = partData.value
                        "updated_at" -> updated_at = partData.value
                        "total_stack" -> total_stack = partData.value.toLongOrNull()
                        "brand" -> brand = partData.value
                        "weight" -> weight = partData.value.toDoubleOrNull()
                        "dimensions" -> dimensions = partData.value
                        "isAvailable" -> isAvailable = partData.value.toBoolean()
                        "discountPrice" -> discountPrice = partData.value.toLongOrNull()
                        "promotionDescription" -> promotionDescription = partData.value
                        "averageRating" -> averageRating = partData.value.toDoubleOrNull()
                    }
                }

                else -> {

                }
            }
        }
        try {
            val product = db.insert(
                name ?: return@post call.respondText("Name Missing", status = HttpStatusCode.BadRequest),
                description ?: return@post call.respondText("Description Missing", status = HttpStatusCode.BadRequest),
                price ?: return@post call.respondText("Price Missing or Invalid", status = HttpStatusCode.BadRequest),
                categoryId ?: return@post call.respondText(
                    "Category ID Missing or Invalid",
                    status = HttpStatusCode.BadRequest
                ),
                categoryTitle ?: return@post call.respondText(
                    "Category Title Missing",
                    status = HttpStatusCode.BadRequest
                ),
                imageUrl ?: return@post call.respondText("Image URL Missing", status = HttpStatusCode.BadRequest),
                created_at ?: return@post call.respondText("Created At Missing", status = HttpStatusCode.BadRequest),
                updated_at ?: return@post call.respondText("Updated At Missing", status = HttpStatusCode.BadRequest),
                total_stack ?: return@post call.respondText(
                    "Total Stack Missing or Invalid",
                    status = HttpStatusCode.BadRequest
                ),
                brand ?: return@post call.respondText("Brand Missing", status = HttpStatusCode.BadRequest),
                weight ?: return@post call.respondText("Weight Missing or Invalid", status = HttpStatusCode.BadRequest),
                dimensions ?: return@post call.respondText("Dimensions Missing", status = HttpStatusCode.BadRequest),
                isAvailable ?: false,
                discountPrice ?: return@post call.respondText(
                    "Discount Price Missing or Invalid",
                    status = HttpStatusCode.BadRequest
                ),
                promotionDescription ?: return@post call.respondText(
                    "Promotion Description Missing or Invalid",
                    status = HttpStatusCode.BadRequest
                ),
                averageRating ?: return@post call.respondText(
                    "Average Rating Missing or Invalid",
                    status = HttpStatusCode.BadRequest
                )
            )
            product?.id?.let {
                call.respond(
                    status = HttpStatusCode.Created,
                    "Product Created Successfully: $product"
                )
            }

        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                "Error While Creating Product: ${e.message}"
            )
        }
    }
    get("v1/products") {
        try {
            val products = db.getAllProducts()
            if (products?.isNotEmpty() == true) {
                call.respond(HttpStatusCode.OK, products)
            } else {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    "No Products Found..."
                )
            }

        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.BadRequest,
                "Error While Fetching Products ${e.message}"
            )
        }
    }

    get("v1/products/{id}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            text = "No ID Found...",
            status = HttpStatusCode.BadRequest
        )
        try {
            val products = db.deleteProductById(id.toLong())
            if (products == null) {
                call.respond(HttpStatusCode.BadRequest, "No Products Found")
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    products
                )
            }

        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.BadRequest,
                "Error While Fetching Products ${e.message}"
            )
        }
    }


    delete("v1/products/{id}") {
        val id = call.parameters["id"] ?: return@delete call.respond(
            status = HttpStatusCode.BadRequest,
            message = "Invalid Id"
        )
        try {
            val products = db.deleteProductById(id.toLong())
            if (products == 1) {
                call.respond(
                    HttpStatusCode.OK,
                    "Product Deleted Successfully $$products"
                )
            } else {
                call.respond(HttpStatusCode.BadRequest, "Id Not Found...")
            }
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.BadRequest,
                "Error While Deleting Products ${e.message}"
            )
        }
    }

    put("v1/products/{id}") {
        val id = call.parameters["id"]?.toLongOrNull() ?: return@put call.respondText(
            text = "Invalid or Missing Product ID",
            status = HttpStatusCode.BadRequest
        )
        val multipart = call.receiveMultipart()

        var name: String? = null
        var description: String? = null
        var price: Long? = null
        var categoryId: Long? = null
        var categoryTitle: String? = null
        var imageUrl: String? = null
        var created_at: String? = null
        var updated_at: String? = null
        var total_stack: Long? = null
        var brand: String? = null
        var weight: Double? = null
        var dimensions: String? = null
        var isAvailable: Boolean? = null
        var discountPrice: Long? = null
        var promotionDescription: String? = null
        var averageRating: Double? = null

        multipart.forEachPart { partData ->
            when (partData) {
                is PartData.FileItem -> {
                    val fileName = partData.originalFileName?.replace(" ","_") ?: "image_${System.currentTimeMillis()}"
                    val file = File("upload/products", fileName)
                    partData.streamProvider().use { input ->
                        file.outputStream().buffered().use { output ->
                            input.copyTo(output)
                        }
                    }
                    imageUrl = "/upload/products/$fileName"
                }
                is PartData.FormItem -> {
                    when (partData.name) {
                        "name" -> name = partData.value
                        "description" -> description = partData.value
                        "price" -> price = partData.value.toLongOrNull()
                        "categoryId" -> categoryId = partData.value.toLongOrNull()
                        "categoryTitle" -> categoryTitle = partData.value
                        "created_at" -> created_at = partData.value
                        "updated_at" -> updated_at = partData.value
                        "total_stack" -> total_stack = partData.value.toLongOrNull()
                        "brand" -> brand = partData.value
                        "weight" -> weight = partData.value.toDoubleOrNull()
                        "dimensions" -> dimensions = partData.value
                        "isAvailable" -> isAvailable = partData.value.toBoolean()
                        "discountPrice" -> discountPrice = partData.value.toLongOrNull()
                        "promotionDescription" -> promotionDescription = partData.value
                        "averageRating" -> averageRating = partData.value.toDoubleOrNull()
                    }
                }
                else -> {

                }
            }
        }
        try {
            val result = db.updateProductById(
                id,
                name ?: return@put call.respondText("Name Missing", status = HttpStatusCode.BadRequest),
                description ?: return@put call.respondText("Description Missing", status = HttpStatusCode.BadRequest),
                price ?: return@put call.respondText("Price Missing or Invalid", status = HttpStatusCode.BadRequest),
                categoryId ?: return@put call.respondText("Category ID Missing or Invalid", status = HttpStatusCode.BadRequest),
                categoryTitle ?: return@put call.respondText("Category Title Missing", status = HttpStatusCode.BadRequest),
                imageUrl ?: return@put call.respondText("Image URL Missing", status = HttpStatusCode.BadRequest),
                created_at ?: return@put call.respondText("Created At Missing", status = HttpStatusCode.BadRequest),
                updated_at ?: return@put call.respondText("Updated At Missing", status = HttpStatusCode.BadRequest),
                total_stack ?: return@put call.respondText("Total Stack Missing or Invalid", status = HttpStatusCode.BadRequest),
                brand ?: return@put call.respondText("Brand Missing", status = HttpStatusCode.BadRequest),
                weight ?: return@put call.respondText("Weight Missing or Invalid", status = HttpStatusCode.BadRequest),
                dimensions ?: return@put call.respondText("Dimensions Missing", status = HttpStatusCode.BadRequest),
                isAvailable ?: false,
                discountPrice ?: return@put call.respondText("Discount Price Missing or Invalid", status = HttpStatusCode.BadRequest),
                promotionDescription ?: "",
                averageRating ?: 0.0
            )

            if (result != null && result > 0) {
                call.respond(
                    status = HttpStatusCode.OK,
                    "Product Updated Successfully"
                )
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    "Product with ID $id not found"
                )
            }

        }catch (e: Exception){
            call.respond(
                status = HttpStatusCode.BadRequest,
                "Error While Updating Products ${e.message}"
            )
        }

    }

}