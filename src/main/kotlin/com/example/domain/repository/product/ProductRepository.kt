package com.example.domain.repository.product

import com.example.data.local.table.category.CategoryTable
import com.example.data.local.table.db.DatabaseFactory
import com.example.data.local.table.product.ProductTable
import com.example.data.repository.product.ProductDao
import com.example.domain.model.product.Product
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ProductRepository : ProductDao {
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
        averageRating: Double,
        manufacturer: String,
        colors: String
    ): Product? {
        return try {
            transaction {
                val statement = ProductTable.insert { product ->
                    product[ProductTable.name] = name
                    product[ProductTable.description] = description
                    product[ProductTable.price] = price
                    product[ProductTable.categoryId] = categoryId
                    product[ProductTable.categoryTitle] = categoryTitle
                    product[ProductTable.imageUrl] = imageUrl
                    product[ProductTable.created_at] = created_at
                    product[ProductTable.updated_at] = updated_at
                    product[ProductTable.total_stack] = total_stack
                    product[ProductTable.brand] = brand
                    product[ProductTable.weight] = weight
                    product[ProductTable.dimensions] = dimensions
                    product[ProductTable.isAvailable] = isAvailable
                    product[ProductTable.discountPrice] = discountPrice
                    product[ProductTable.promotionDescription] = promotionDescription
                    product[ProductTable.averageRating] = averageRating
                    product[ProductTable.isFeature] = isFeature
                    product[ProductTable.manufacturer] = manufacturer
                    product[ProductTable.colors] = colors
                }
                val firstResult = statement.resultedValues?.firstOrNull()!!
                rowToResult(firstResult)
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getAllProducts(): List<Product>? {
        return DatabaseFactory.dbQuery {
            ProductTable.selectAll()
                .mapNotNull {
                    rowToResult(it)
                }
        }
    }

    override suspend fun getProductById(id: Long): Product? {
        return DatabaseFactory.dbQuery {
            ProductTable.select { ProductTable.id.eq(id) }
                .map {
                    rowToResult(it)
                }.singleOrNull()
        }
    }

    override suspend fun deleteProductById(id: Long): Int? {
        return DatabaseFactory.dbQuery {
            ProductTable.deleteWhere { ProductTable.id.eq(id) }
        }
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
        averageRating: Double,
        isFeature: Boolean,
        manufacturer: String,
        colors: String
    ): Int? {
        return DatabaseFactory.dbQuery {
            ProductTable.update({ ProductTable.id.eq(id) }) { product ->
                product[ProductTable.name] = name
                product[ProductTable.description] = description
                product[ProductTable.price] = price
                product[ProductTable.categoryId] = categoryId
                product[ProductTable.categoryTitle] = categoryTitle
                product[ProductTable.imageUrl] = imageUrl
                product[ProductTable.created_at] = created_at
                product[ProductTable.updated_at] = updated_at
                product[ProductTable.total_stack] = total_stack
                product[ProductTable.brand] = brand
                product[ProductTable.weight] = weight
                product[ProductTable.dimensions] = dimensions
                product[ProductTable.isAvailable] = isAvailable
                product[ProductTable.discountPrice] = discountPrice
                product[ProductTable.promotionDescription] = promotionDescription
                product[ProductTable.averageRating] = averageRating
                product[ProductTable.isFeature] = isFeature
                product[ProductTable.manufacturer] = manufacturer
                product[ProductTable.colors] = colors
            }
        }
    }

    private fun rowToResult(row: ResultRow): Product? {
        return if (row == null) {
            null
        } else {
            val categoryTitle = CategoryTable.select { CategoryTable.id eq row[ProductTable.categoryId] }
                .map { it[CategoryTable.name] }
                .singleOrNull() ?: ""
            Product(
                id = row[ProductTable.id],
                name = row[ProductTable.name],
                description = row[ProductTable.description],
                price = row[ProductTable.price],
                categoryId = row[ProductTable.categoryId],
                categoryTitle = categoryTitle,
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
                averageRating = row[ProductTable.averageRating],
                isFeatured = row[ProductTable.isFeature],
                manufacturer = row[ProductTable.manufacturer],
                colors = row[ProductTable.colors]
            )
        }
    }
}