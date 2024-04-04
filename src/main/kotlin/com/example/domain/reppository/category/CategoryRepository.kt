package com.example.domain.reppository.category

import com.example.data.local.table.category.CategoryTable

import com.example.data.local.table.db.DatabaseFactory
import com.example.data.repository.category.CategoryDao
import com.example.domain.model.category.Category
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement

class CategoryRepository : CategoryDao {
    override suspend fun insert(name: String, description: String, isVisible: Boolean, imageUrl: String): Category? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = CategoryTable.insert { category ->
                category[CategoryTable.name] = name
                category[CategoryTable.description] = description
                category[CategoryTable.isVisible] = isVisible
                category[CategoryTable.imageUrl] = imageUrl
            }
        }
        return rowToResult(statement?.resultedValues?.get(0)!!)
    }

    override suspend fun getAllCategories(): List<Category>? {
        return DatabaseFactory.dbQuery {
            CategoryTable.selectAll()
                .mapNotNull {
                    rowToResult(it)
                }
        }
    }

    override suspend fun getCategoryById(id: Long): Category? {
        return DatabaseFactory.dbQuery {
            CategoryTable.select(CategoryTable.id.eq(id))
                .map {
                    rowToResult(it)
                }.single()
        }
    }

    override suspend fun deleteCategoryById(id: Long): Int? {
        return DatabaseFactory.dbQuery {
            CategoryTable.deleteWhere { CategoryTable.id.eq(id) }
        }
    }

    override suspend fun updateCategoryById(
        id: Long,
        name: String,
        description: String,
        isVisible: Boolean,
        imageUrl: String
    ): Int? {
       return DatabaseFactory.dbQuery {
           CategoryTable.update({CategoryTable.id.eq(id)}){category ->
               category[CategoryTable.id]= id
               category[CategoryTable.name] = name
               category[CategoryTable.description]= description
               category[CategoryTable.isVisible] = isVisible
               category[CategoryTable.imageUrl]= imageUrl
           }
       }
    }

    private fun rowToResult(row: ResultRow): Category? {
        if (row == null) {
            return null
        } else {
            return Category(
                id = row[CategoryTable.id],
                name = row[CategoryTable.name],
                description = row[CategoryTable.description],
                isVisible = row[CategoryTable.isVisible],
                imageUrl = row[CategoryTable.imageUrl]
            )
        }
    }
}