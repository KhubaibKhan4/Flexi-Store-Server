package com.example.plugins

import com.example.domain.model.login.LoginResponse
import com.example.domain.repository.books.BooksRepository
import com.example.domain.repository.cart.CartRepository
import com.example.domain.repository.category.CategoryRepository
import com.example.domain.repository.product.ProductRepository
import com.example.domain.repository.promotion.PromotionRepository
import com.example.domain.repository.user.UsersRepository
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat

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
    post("v1/login") {
        val parameters = call.receive<Parameters>()
        val email = parameters["email"] ?: return@post call.respondText(
            text = "Email Missing",
            status = HttpStatusCode.Unauthorized
        )
        val password = parameters["password"] ?: return@post call.respondText(
            text = "Password Missing",
            status = HttpStatusCode.Unauthorized
        )

        try {
            val user = db.login(email, password)
            if (user != null) {
                val loginResponse = LoginResponse("Login Successful", user)
                val responseJson = Json { prettyPrint = true }.encodeToString(loginResponse)
                call.respondText(responseJson, ContentType.Application.Json)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid Email or Password")
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Error during login: ${e.message}")
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
        val uploadDir = File("upload/products/categories")
        if (!uploadDir.exists()){
            uploadDir.mkdirs()
        }

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
                    val fileName = part.originalFileName?.replace(" ", "_") ?: "image${System.currentTimeMillis()}"
                    val file = File(uploadDir, fileName)
                    part.streamProvider().use { input ->
                        file.outputStream().buffered().use { output ->
                            input.copyTo(output)
                        }

                    }
                    imageUrl = "/upload/products/categories/${fileName}"
                }

                else -> {}
            }
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
        val uploadDir = File("upload/products/categories")
        if (!uploadDir.exists()){
            uploadDir.mkdirs()
        }

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
                    val fileName = part.originalFileName?.replace(" ", "_") ?: "image${System.currentTimeMillis()}"
                    val file = File(uploadDir, fileName)
                    part.streamProvider().use { input ->
                        file.outputStream().buffered().use { output ->
                            input.copyTo(output)
                        }

                    }
                    imageUrl = "/upload/products/categories/${fileName}"
                }

                else -> {}
            }
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
        var isFeature: Boolean? = null
        var manufacturer: String? = null
        var colors : String? = null
        val uploadDir = File("/upload/products/")
        if (!uploadDir.exists()) {
            uploadDir.mkdirs()
        }
        multipart.forEachPart { partData ->
            when (partData) {
                is PartData.FileItem -> {
                    val fileName = partData.originalFileName?.replace(" ", "_") ?: "image${System.currentTimeMillis()}"
                    val file = File("upload/products", fileName)
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
                        "isFeature" -> isFeature = partData.value.toBoolean()
                        "manufacturer" -> manufacturer = partData.value
                        "colors" -> colors = partData.value
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
                ),
                isFeature ?: return@post call.respondText(
                    "isFeature Missing or Invalid",
                    status = HttpStatusCode.BadRequest
                ),
                manufacturer ?:return@post call.respondText(
                    "manufacturer Missing or Invalid",
                    status = HttpStatusCode.BadRequest
                ),
                colors ?:return@post call.respondText(
                    "colors Missing or Invalid",
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
            val products = db.getProductById(id.toLong())
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
        var isFeature: Boolean? = null
        var manufacturer: String? = null
        var colors : String? = null

        multipart.forEachPart { partData ->
            when (partData) {
                is PartData.FileItem -> {
                    val fileName = partData.originalFileName?.replace(" ", "_") ?: "image_${System.currentTimeMillis()}"
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
                        "isFeature" -> isFeature = partData.value.toBoolean()
                        "manufacturer" -> manufacturer = partData.value
                        "colors" -> colors = partData.value
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
                categoryId ?: return@put call.respondText(
                    "Category ID Missing or Invalid",
                    status = HttpStatusCode.BadRequest
                ),
                categoryTitle ?: return@put call.respondText(
                    "Category Title Missing",
                    status = HttpStatusCode.BadRequest
                ),
                imageUrl ?: return@put call.respondText("Image URL Missing", status = HttpStatusCode.BadRequest),
                created_at ?: return@put call.respondText("Created At Missing", status = HttpStatusCode.BadRequest),
                updated_at ?: return@put call.respondText("Updated At Missing", status = HttpStatusCode.BadRequest),
                total_stack ?: return@put call.respondText(
                    "Total Stack Missing or Invalid",
                    status = HttpStatusCode.BadRequest
                ),
                brand ?: return@put call.respondText("Brand Missing", status = HttpStatusCode.BadRequest),
                weight ?: return@put call.respondText("Weight Missing or Invalid", status = HttpStatusCode.BadRequest),
                dimensions ?: return@put call.respondText("Dimensions Missing", status = HttpStatusCode.BadRequest),
                isAvailable ?: false,
                discountPrice ?: return@put call.respondText(
                    "Discount Price Missing or Invalid",
                    status = HttpStatusCode.BadRequest
                ),
                promotionDescription ?: "",
                averageRating ?: 0.0,
                isFeature ?: return@put call.respondText(
                    "isFeature Missing or Invalid",
                    status = HttpStatusCode.BadRequest
                ),
                manufacturer ?:return@put call.respondText(
                    "manufacturer Missing or Invalid",
                    status = HttpStatusCode.BadRequest
                ),
                colors ?:return@put call.respondText(
                    "colors Missing or Invalid",
                    status = HttpStatusCode.BadRequest
                )
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

        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.BadRequest,
                "Error While Updating Products ${e.message}"
            )
        }

    }

}

fun Route.promotions(
    db: PromotionRepository
) {
    post("v1/promotions") {
        val multipart = call.receiveMultipart()
        var title: String? = null
        var description: String? = null
        var imageUrl: String? = null
        var startDate: Long? = null
        var endDate: Long? = null
        var enable: Boolean? = null
        val uploadDir = File("upload/products/promotions")
        if (!uploadDir.exists()){
            uploadDir.mkdirs()
        }
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")

        multipart.forEachPart { partData ->
            when (partData) {
                is PartData.FileItem -> {
                    val fileName = partData.originalFileName?.replace(" ","_") ?: "name/${System.currentTimeMillis()}"
                    val file = File(uploadDir,fileName)
                    partData.streamProvider().use { input->
                        file.outputStream().use { output->
                            input.copyTo(output)
                        }
                    }
                    imageUrl = "/upload/products/promotions/$fileName"
                }

                is PartData.FormItem -> {
                    when(partData.name){
                        "title" -> title = partData.value
                        "description" -> description = partData.value
                        "startDate" ->startDate= partData.value?.let { dateFormat.parse(it)?.time }
                        "endDate" -> endDate = partData.value?.let { dateFormat.parse(it)?.time }
                        "enable" -> enable = partData.value.toBoolean()
                    }
                }
                else -> {}
            }
        }
        try {
            val products = db.insert(
                title  = title ?: return@post call.respond(HttpStatusCode.BadRequest, "Title Missing"),
                description = description ?: return@post call.respond(HttpStatusCode.BadRequest, "Description Missing"),
                imageUrl = imageUrl ?: return@post call.respond(HttpStatusCode.BadRequest, "Image File Missing"),
                startDate = startDate ?: return@post call.respond(HttpStatusCode.BadRequest, "Start Date Missing"),
                endDate = endDate ?: return@post call.respond(HttpStatusCode.BadRequest,"End Date Missing"),
                enable = enable ?: return@post call.respond(HttpStatusCode.BadRequest,"Enabled Missing")
            )
            products.let {
                call.respond(
                    HttpStatusCode.Created,
                    "Promotion Product Added Successfully... $products"
                )
            }

        }catch (e: Exception){
            call.respond(
                status = HttpStatusCode.Unauthorized,
                "Error While Uploading Promotions Products : ${e.message}"
            )
        }
    }
    delete("v1/promotions/{id}") {
        val id = call.parameters["id"]?.toLongOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")

        try {
            val deletedCount = db.deletePromotionById(id)
            if (deletedCount != null && deletedCount > 0) {
                call.respond(HttpStatusCode.OK, "Promotion with ID $id deleted successfully")
            } else {
                call.respond(HttpStatusCode.NotFound, "Promotion with ID $id not found")
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to delete promotion: ${e.message}")
        }
    }
    get("v1/promotions") {
        try {
            val promotion = db.getPromotionsList()
            if (promotion.isNullOrEmpty() ==true) {
                call.respond(HttpStatusCode.NotFound, "No Promotion Items Available inside the Database.dd ")
            } else {
                call.respond(HttpStatusCode.OK, promotion)
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to retrieve promotion: ${e.message}")
        }
    }
    get("v1/promotions/{id}") {
        val id = call.parameters["id"]?.toLongOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")

        try {
            val promotion = db.getPromotionById(id)
            if (promotion != null) {
                call.respond(HttpStatusCode.OK, promotion)
            } else {
                call.respond(HttpStatusCode.NotFound, "Promotion with ID $id not found")
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to retrieve promotion: ${e.message}")
        }
    }
    put ("v1/promotions/{id}"){
        val id = call.parameters["id"] ?: return@put call.respond(
            HttpStatusCode.BadRequest,
            "Id Missing"
        )
        val multipart = call.receiveMultipart()
        var title: String? = null
        var description: String? = null
        var imageUrl: String? = null
        var startDate: Long? = null
        var endDate: Long? = null
        var enable: Boolean? = null
        val uploadDir = File("upload/products/promotions")
        if (!uploadDir.exists()){
            uploadDir.mkdirs()
        }
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")

        multipart.forEachPart { partData ->
            when (partData) {
                is PartData.FileItem -> {
                    val fileName = partData.originalFileName?.replace(" ","_") ?: "name/${System.currentTimeMillis()}"
                    val file = File(uploadDir,fileName)
                    partData.streamProvider().use { input->
                        file.outputStream().use { output->
                            input.copyTo(output)
                        }
                    }
                    imageUrl = "/upload/products/promotions/$fileName"
                }

                is PartData.FormItem -> {
                    when(partData.name){
                        "title" -> title = partData.value
                        "description" -> description = partData.value
                        "startDate" ->startDate= partData.value?.let { dateFormat.parse(it)?.time }
                        "endDate" -> endDate = partData.value?.let { dateFormat.parse(it)?.time }
                        "enable" -> enable = partData.value.toBoolean()
                    }
                }
                else -> {}
            }
        }
        try {
            val products = db.updatePromotion(
                id = id.toLong(),
                title  = title ?: return@put call.respond(HttpStatusCode.BadRequest, "Title Missing"),
                description = description ?: return@put call.respond(HttpStatusCode.BadRequest, "Description Missing"),
                imageUrl = imageUrl ?: return@put call.respond(HttpStatusCode.BadRequest, "Image File Missing"),
                startDate = startDate ?: return@put call.respond(HttpStatusCode.BadRequest, "Start Date Missing"),
                endDate = endDate ?: return@put call.respond(HttpStatusCode.BadRequest,"End Date Missing"),
                enable = enable ?: return@put call.respond(HttpStatusCode.BadRequest,"Enabled Missing")
            )
            if (products != null) {
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
                status = HttpStatusCode.Unauthorized,
                "Error While Uploading Promotions Products : ${e.message}"
            )
        }
    }
}
fun Route.books(
    db: BooksRepository
){
    post("v1/books") {
        val multipart = call.receiveMultipart()
        var title: String? = null
        var author: String? = null
        var description: String? = null
        var price: Double? = null
        var category: String? = null
        var imageUrl: String? = null
        var isbn: String? = null
        var pageCount: Int? = null
        var publisher: String? = null
        var publicationYear: Int? = null
        var averageRating: Double? = null
        var stock: Int? = null
        var dimensions: String? = null
        var weight: Double? = null
        var language: String? = null
        var format: String? = null
        var edition: String? = null
        var genre: String? = null
        var publicationDate: String? = null
        var binding: String? = null
        var tableOfContents: String? = null
        var awards: String? = null
        var contributors: String? = null
        var annotations: String? = null
        var tags: String? = null

        val uploadDir = File("upload/products/books")
        if (!uploadDir.exists()){
            uploadDir.mkdirs()
        }

        multipart.forEachPart { partData ->
            when (partData) {
                is PartData.FileItem -> {
                    val fileName = partData.originalFileName?.replace(" ","_") ?: "name/${System.currentTimeMillis()}"
                    val file = File(uploadDir,fileName)
                    partData.streamProvider().use { input->
                        file.outputStream().use { output->
                            input.copyTo(output)
                        }
                    }
                    imageUrl = "/upload/products/books/$fileName"
                }

                is PartData.FormItem -> {
                    when (partData.name) {
                        "title" -> title = partData.value
                        "author" -> author = partData.value
                        "description" -> description = partData.value
                        "price" -> price = partData.value.toDoubleOrNull()
                        "category" -> category = partData.value
                        "isbn" -> isbn = partData.value
                        "pageCount" -> pageCount = partData.value.toIntOrNull()
                        "publisher" -> publisher = partData.value
                        "publicationYear" -> publicationYear = partData.value.toIntOrNull()
                        "averageRating" -> averageRating = partData.value.toDoubleOrNull()
                        "stock" -> stock = partData.value.toIntOrNull()
                        "dimensions" -> dimensions = partData.value
                        "weight" -> weight = partData.value.toDoubleOrNull()
                        "language" -> language = partData.value
                        "format" -> format = partData.value
                        "edition" -> edition = partData.value
                        "genre" -> genre = partData.value
                        "publicationDate" -> publicationDate = partData.value
                        "binding" -> binding = partData.value
                        "tableOfContents" -> tableOfContents = partData.value
                        "awards" -> awards = partData.value
                        "contributors" -> contributors = partData.value
                        "annotations" -> annotations = partData.value
                        "tags" -> tags = partData.value
                    }
                }

                else -> {

                }
            }
        }

        try {
            val book = db.insert(
                title ?: return@post call.respondText("Title Missing", status = HttpStatusCode.BadRequest),
                author ?: return@post call.respondText("Author Missing", status = HttpStatusCode.BadRequest),
                description ?: return@post call.respondText("Description Missing", status = HttpStatusCode.BadRequest),
                price ?: return@post call.respondText("Price Missing or Invalid", status = HttpStatusCode.BadRequest),
                category ?: return@post call.respondText("Category Missing", status = HttpStatusCode.BadRequest),
                imageUrl ?: return@post call.respondText("Image URL Missing", status = HttpStatusCode.BadRequest),
                isbn ?: return@post call.respondText("ISBN Missing", status = HttpStatusCode.BadRequest),
                pageCount ?: return@post call.respondText("Page Count Missing or Invalid", status = HttpStatusCode.BadRequest),
                publisher ?: return@post call.respondText("Publisher Missing", status = HttpStatusCode.BadRequest),
                publicationYear ?: return@post call.respondText("Publication Year Missing or Invalid", status = HttpStatusCode.BadRequest),
                averageRating ?: return@post call.respondText("Average Rating Missing or Invalid", status = HttpStatusCode.BadRequest),
                stock ?: return@post call.respondText("Stock Missing or Invalid", status = HttpStatusCode.BadRequest),
                dimensions ?: return@post call.respondText("Dimensions Missing", status = HttpStatusCode.BadRequest),
                weight ?: return@post call.respondText("Weight Missing or Invalid", status = HttpStatusCode.BadRequest),
                language ?: return@post call.respondText("Language Missing", status = HttpStatusCode.BadRequest),
                format ?: return@post call.respondText("Format Missing", status = HttpStatusCode.BadRequest),
                edition ?: return@post call.respondText("Edition Missing", status = HttpStatusCode.BadRequest),
                genre ?: return@post call.respondText("Genre Missing", status = HttpStatusCode.BadRequest),
                publicationDate ?: return@post call.respondText("Publication Date Missing", status = HttpStatusCode.BadRequest),
                binding ?: return@post call.respondText("Binding Missing", status = HttpStatusCode.BadRequest),
                tableOfContents ?: return@post call.respondText("Table of Contents Missing", status = HttpStatusCode.BadRequest),
                awards ?: return@post call.respondText("Awards Missing", status = HttpStatusCode.BadRequest),
                contributors ?: return@post call.respondText("Contributors Missing", status = HttpStatusCode.BadRequest),
                annotations,
                tags ?: return@post call.respondText("Tags Missing", status = HttpStatusCode.BadRequest)
            )
            book?.id?.let {
                call.respond(
                    status = HttpStatusCode.Created,
                    "Book Created Successfully: $book"
                )
            }

        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                "Error While Creating Book: ${e.message}"
            )
        }
    }

    put("v1/books/{id}") {
        val id = call.parameters["id"]?.toLongOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")

        val multipart = call.receiveMultipart()
        var title: String? = null
        var author: String? = null
        var description: String? = null
        var price: Double? = null
        var category: String? = null
        var imageUrl: String? = null
        var isbn: String? = null
        var pageCount: Int? = null
        var publisher: String? = null
        var publicationYear: Int? = null
        var averageRating: Double? = null
        var stock: Int? = null
        var dimensions: String? = null
        var weight: Double? = null
        var language: String? = null
        var format: String? = null
        var edition: String? = null
        var genre: String? = null
        var publicationDate: String? = null
        var binding: String? = null
        var tableOfContents: String? = null
        var awards: String? = null
        var contributors: String? = null
        var annotations: String? = null
        var tags: String? = null

        val uploadDir = File("upload/products/promotions")
        if (!uploadDir.exists()){
            uploadDir.mkdirs()
        }

        multipart.forEachPart { partData ->
            when (partData) {
                is PartData.FormItem -> {
                    when (partData.name) {
                        "title" -> title = partData.value
                        "author" -> author = partData.value
                        "description" -> description = partData.value
                        "price" -> price = partData.value.toDoubleOrNull()
                        "category" -> category = partData.value
                        "imageUrl" -> imageUrl = partData.value
                        "isbn" -> isbn = partData.value
                        "pageCount" -> pageCount = partData.value.toIntOrNull()
                        "publisher" -> publisher = partData.value
                        "publicationYear" -> publicationYear = partData.value.toIntOrNull()
                        "averageRating" -> averageRating = partData.value.toDoubleOrNull()
                        "stock" -> stock = partData.value.toIntOrNull()
                        "dimensions" -> dimensions = partData.value
                        "weight" -> weight = partData.value.toDoubleOrNull()
                        "language" -> language = partData.value
                        "format" -> format = partData.value
                        "edition" -> edition = partData.value
                        "genre" -> genre = partData.value
                        "publicationDate" -> publicationDate = partData.value
                        "binding" -> binding = partData.value
                        "tableOfContents" -> tableOfContents = partData.value
                        "awards" -> awards = partData.value
                        "contributors" -> contributors = partData.value
                        "annotations" -> annotations = partData.value
                        "tags" -> tags = partData.value
                    }
                }

                is PartData.FileItem -> {
                    val fileName = partData.originalFileName?.replace(" ", "_") ?: "image${System.currentTimeMillis()}"
                    val file = File("upload/products/promotions", fileName)
                    partData.streamProvider().use { input ->
                        file.outputStream().buffered().use { output ->
                            input.copyTo(output)
                        }
                    }
                    imageUrl = "/upload/products/promotions/${fileName}"
                }

                else -> {

                }
            }
        }

        try {
            val updatedCount = db.updateBookById(
                id = id,
                title = title ?: return@put call.respond(HttpStatusCode.BadRequest, "Title is required"),
                author = author ?: return@put call.respond(HttpStatusCode.BadRequest, "Author is required"),
                description = description ?: return@put call.respond(HttpStatusCode.BadRequest, "Description is required"),
                price = price ?: return@put call.respond(HttpStatusCode.BadRequest, "Price is required"),
                category = category ?: return@put call.respond(HttpStatusCode.BadRequest, "Category is required"),
                imageUrl = imageUrl ?: return@put call.respond(HttpStatusCode.BadRequest, "Image URL is required"),
                isbn = isbn ?: return@put call.respond(HttpStatusCode.BadRequest, "ISBN is required"),
                pageCount = pageCount ?: return@put call.respond(HttpStatusCode.BadRequest, "Page count is required"),
                publisher = publisher ?: return@put call.respond(HttpStatusCode.BadRequest, "Publisher is required"),
                publicationYear = publicationYear ?: return@put call.respond(HttpStatusCode.BadRequest, "Publication year is required"),
                averageRating = averageRating ?: return@put call.respond(HttpStatusCode.BadRequest, "Average rating is required"),
                stock = stock ?: return@put call.respond(HttpStatusCode.BadRequest, "Stock is required"),
                dimensions = dimensions ?: return@put call.respond(HttpStatusCode.BadRequest, "Dimensions is required"),
                weight = weight ?: return@put call.respond(HttpStatusCode.BadRequest, "Weight is required"),
                language = language ?: return@put call.respond(HttpStatusCode.BadRequest, "Language is required"),
                format = format ?: return@put call.respond(HttpStatusCode.BadRequest, "Format is required"),
                edition = edition ?: return@put call.respond(HttpStatusCode.BadRequest, "Edition is required"),
                genre = genre ?: return@put call.respond(HttpStatusCode.BadRequest, "Genre is required"),
                publicationDate = publicationDate ?: return@put call.respond(HttpStatusCode.BadRequest, "Publication date is required"),
                binding = binding ?: return@put call.respond(HttpStatusCode.BadRequest, "Binding is required"),
                tableOfContents = tableOfContents ?: return@put call.respond(HttpStatusCode.BadRequest, "Table of contents is required"),
                awards = awards ?: return@put call.respond(HttpStatusCode.BadRequest, "Awards is required"),
                contributors = contributors ?: return@put call.respond(HttpStatusCode.BadRequest, "Contributors is required"),
                annotations = annotations,
                tags = tags ?: return@put call.respond(HttpStatusCode.BadRequest, "Tags is required")
            )

            if (updatedCount != null && updatedCount > 0) {
                call.respond(HttpStatusCode.OK, "Book with ID $id updated successfully")
            } else {
                call.respond(HttpStatusCode.NotFound, "Book with ID $id not found")
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to update book: ${e.message}")
        }
    }


    delete("v1/books/{id}") {
        val id = call.parameters["id"]?.toLongOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")

        try {
            val deletedCount = db.deleteBookById(id)
            if (deletedCount != null && deletedCount > 0) {
                call.respond(HttpStatusCode.OK, "Book with ID $id deleted successfully")
            } else {
                call.respond(HttpStatusCode.NotFound, "Book with ID $id not found")
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to delete book: ${e.message}")
        }
    }

    get("v1/books/{id}") {
        val id = call.parameters["id"]?.toLongOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")

        try {
            val book = db.getBookById(id)
            if (book != null) {
                call.respond(HttpStatusCode.OK, book)
            } else {
                call.respond(HttpStatusCode.NotFound, "Book with ID $id not found")
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to retrieve book: ${e.message}")
        }
    }

    get("v1/books") {
        try {
            val books = db.getAllBooks()
            if (books.isNullOrEmpty()) {
                call.respond(HttpStatusCode.NotFound, "No books found")
            } else {
                call.respond(HttpStatusCode.OK, books)
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to retrieve books: ${e.message}")
        }
    }

}
fun Route.carts(
    db: CartRepository
) {
    post("v1/cart") {
        val parameters = call.receive<Parameters>()
        val productId = parameters["productId"]?.toLongOrNull()
            ?: return@post call.respondText(
                text = "Product ID Missing",
                status = HttpStatusCode.BadRequest
            )
        val quantity = parameters["quantity"]?.toIntOrNull()
            ?: return@post call.respondText(
                text = "Quantity Missing or Invalid",
                status = HttpStatusCode.BadRequest
            )
        val userId = parameters["userId"]?.toLongOrNull()
            ?: return@post call.respondText(
                text = "User ID Missing or Invalid",
                status = HttpStatusCode.BadRequest
            )
        try {
            val cartItem = db.insert(productId, quantity, userId)
            cartItem.let {
                call.respond(
                    status = HttpStatusCode.OK,
                    cartItem ?: "Error while adding item to cart"
                )
            }
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                "Error While Adding Item to Cart: ${e.message}"
            )
        }
    }

    get("v1/cart") {
        try {
            val cartItems = db.getAllCart()
            if (cartItems != null) {
                call.respond(HttpStatusCode.OK, cartItems)
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    "Cart Items Not Found for User ID: $cartItems"
                )
            }
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                "Error While Fetching Cart Items: ${e.message}"
            )
        }
    }
    get("v1/cart/{userId}") {
        val userId = call.parameters["userId"]?.toLongOrNull()
            ?: return@get call.respondText(
                text = "User ID Missing or Invalid",
                status = HttpStatusCode.BadRequest
            )
        try {
            val cartItems = db.getCartItemByUserId(userId)
            if (cartItems != null) {
                call.respond(HttpStatusCode.OK, cartItems)
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    "Cart Items Not Found for User ID: $userId"
                )
            }
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                "Error While Fetching Cart Items: ${e.message}"
            )
        }
    }

    delete("v1/cart/{userId}") {
        val userId = call.parameters["userId"]?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "User ID Missing or Invalid",
                status = HttpStatusCode.BadRequest
            )
        try {
            val deletedItemsCount = db.deleteCartByUserId(userId)
            call.respond(
                status = HttpStatusCode.OK,
                "Deleted $deletedItemsCount items from cart for user ID: $userId"
            )
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                "Error While Deleting Cart Items: ${e.message}"
            )
        }
    }

    put("v1/cart/{userId}") {
        val userId = call.parameters["userId"]?.toLongOrNull()
            ?: return@put call.respondText(
                text = "User ID Missing or Invalid",
                status = HttpStatusCode.BadRequest
            )

        val parameters = call.receive<Parameters>()
        val productId = parameters["productId"]?.toLongOrNull()
            ?: return@put call.respondText(
                text = "Product ID Missing or Invalid",
                status = HttpStatusCode.BadRequest
            )
        val quantity = parameters["quantity"]?.toIntOrNull()
            ?: return@put call.respondText(
                text = "Quantity Missing or Invalid",
                status = HttpStatusCode.BadRequest
            )

        try {
            db.update(productId, quantity, userId)
            call.respond(HttpStatusCode.OK, "Cart item updated successfully")
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                "Error While Updating Cart Item: ${e.message}"
            )
        }
    }
}