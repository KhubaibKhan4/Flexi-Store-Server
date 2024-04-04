package com.example.domain.reppository.product

import com.example.data.local.table.product.ProductTable
import com.example.data.repository.product.ProductDao
import com.example.domain.model.product.Product
import org.jetbrains.exposed.sql.ResultRow

class ProductRepository: ProductDao {
    override suspend fun insert(
        name: String,
        description: String,
        price: Long,
        categoryId: Long,
        categoryTitle: String,
        imageUrl: String,
        created_at: String,
        updated_at: String,
        total_stack: Long,
        brand: String,
        weight: Double,
        dimensions: String,
        isAvailable: Boolean,
        discountPrice: Long,
        promotionDescription: String,
        averageRating: Double
    ): Product? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProducts(): List<Product>? {
        TODO("Not yet implemented")
    }

    override suspend fun getProductById(id: Long): Product? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProductById(id: Long): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun updateProductById(
        id: Long,
        name: String,
        description: String,
        price: Long,
        categoryId: Long,
        categoryTitle: String,
        imageUrl: String,
        created_at: String,
        updated_at: String,
        total_stack: Long,
        brand: String,
        weight: Double,
        dimensions: String,
        isAvailable: Boolean,
        discountPrice: Long,
        promotionDescription: String,
        averageRating: Double
    ): Int? {
        TODO("Not yet implemented")
    }
    private fun rowToResult(row: ResultRow): Product? {
        return if (row == null) {
            null
        } else {
            Product(
                id = row[ProductTable.id],
                name = row[ProductTable.name],
                description = row[ProductTable.description],
                price = row[ProductTable.price],
                categoryId = row[ProductTable.categoryId],
                categoryTitle = row[ProductTable.categoryTitle],
                imageUrl = row[ProductTable.imageUrl],
                created_at = row[ProductTable.created_at],
                updated_at = row[ProductTable.updated_at],
                total_stack = row[ProductTable.total_stack],
                brand = row[ProductTable.brand],
                weight = row[ProductTable.weight],
                dimensions = row[ProductTable.dimensions],
                isAvailable = row[ProductTable.isAvailable],
                discountPrice = row[ProductTable.discountPrice],
                promotionDescription = row[ProductTable.promotionDescription],
                averageRating = row[ProductTable.averageRating]
            )
        }
    }

}