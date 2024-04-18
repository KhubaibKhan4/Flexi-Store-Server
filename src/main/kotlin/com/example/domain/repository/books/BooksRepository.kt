package com.example.domain.repository.books

import com.example.data.local.table.books.BooksTable
import com.example.data.local.table.db.DatabaseFactory
import com.example.data.repository.books.BooksDao
import com.example.domain.model.books.Books
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement

class BooksRepository : BooksDao {
    override suspend fun insert(
        title: String,
        author: String,
        description: String,
        price: Double,
        category: String,
        imageUrl: String,
        isbn: String,
        pageCount: Int,
        publisher: String,
        publicationYear: Int,
        averageRating: Double,
        stock: Int,
        dimensions: String,
        weight: Double,
        language: String,
        format: String,
        edition: String,
        genre: String,
        publicationDate: String,
        binding: String,
        tableOfContents: String,
        awards: String,
        contributors: String,
        annotations: String?,
        tags: String
    ): Books? {
        return try {
            DatabaseFactory.dbQuery {
                val statement = BooksTable.insert { books ->
                    books[BooksTable.title] = title
                    books[BooksTable.author] = author
                    books[BooksTable.description] = description
                    books[BooksTable.price] = price
                    books[BooksTable.category] = category
                    books[BooksTable.imageUrl] = imageUrl
                    books[BooksTable.isbn] = isbn
                    books[BooksTable.pageCount] = pageCount
                    books[BooksTable.publisher] = publisher
                    books[BooksTable.publicationYear] = publicationYear
                    books[BooksTable.averageRating] = averageRating
                    books[BooksTable.stock] = stock
                    books[BooksTable.dimensions] = dimensions
                    books[BooksTable.weight] = weight
                    books[BooksTable.language] = language
                    books[BooksTable.format] = format
                    books[BooksTable.edition] = edition
                    books[BooksTable.genre] = genre
                    books[BooksTable.publicationDate] = publicationDate
                    books[BooksTable.binding] = binding
                    books[BooksTable.tableOfContents] = tableOfContents
                    books[BooksTable.awards] = awards
                    books[BooksTable.contributors] = contributors
                    books[BooksTable.annotations] = annotations
                    books[BooksTable.tags] = tags
                }
                rowToResult(statement.resultedValues?.get(0)!!)
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getAllBooks(): List<Books>? {
        return DatabaseFactory.dbQuery {
            BooksTable.selectAll()
                .mapNotNull { rowToResult(it) }
        }
    }

    override suspend fun getBookById(id: Long): Books? {
        return DatabaseFactory.dbQuery {
            BooksTable.select { BooksTable.id.eq(id)}
                .mapNotNull { rowToResult(it) }
                .singleOrNull()
        }
    }

    override suspend fun deleteBookById(id: Long): Int? {
        return DatabaseFactory.dbQuery {
            BooksTable.deleteWhere { BooksTable.id.eq(id) }
        }
    }


    override suspend fun updateBookById(
        id: Long,
        title: String,
        author: String,
        description: String,
        price: Double,
        category: String,
        imageUrl: String,
        isbn: String,
        pageCount: Int,
        publisher: String,
        publicationYear: Int,
        averageRating: Double,
        stock: Int,
        dimensions: String,
        weight: Double,
        language: String,
        format: String,
        edition: String,
        genre: String,
        publicationDate: String,
        binding: String,
        tableOfContents: String,
        awards: String,
        contributors: String,
        annotations: String?,
        tags: String
    ): Int? {
        return DatabaseFactory.dbQuery {
            BooksTable.update({ BooksTable.id eq id }) { books ->
                books[BooksTable.title] = title
                books[BooksTable.author] = author
                books[BooksTable.description] = description
                books[BooksTable.price] = price
                books[BooksTable.category] = category
                books[BooksTable.imageUrl] = imageUrl
                books[BooksTable.isbn] = isbn
                books[BooksTable.pageCount] = pageCount
                books[BooksTable.publisher] = publisher
                books[BooksTable.publicationYear] = publicationYear
                books[BooksTable.averageRating] = averageRating
                books[BooksTable.stock] = stock
                books[BooksTable.dimensions] = dimensions
                books[BooksTable.weight] = weight
                books[BooksTable.language] = language
                books[BooksTable.format] = format
                books[BooksTable.edition] = edition
                books[BooksTable.genre] = genre
                books[BooksTable.publicationDate] = publicationDate
                books[BooksTable.binding] = binding
                books[BooksTable.tableOfContents] = tableOfContents
                books[BooksTable.awards] = awards
                books[BooksTable.contributors] = contributors
                books[BooksTable.annotations] = annotations
                books[BooksTable.tags] = tags
            }
        }
    }

    private fun rowToResult(row: ResultRow): Books? {
         if (row == null) {
            return null
        } else {
            return Books(
                id = row[BooksTable.id],
                title = row[BooksTable.title],
                author = row[BooksTable.author],
                description = row[BooksTable.description],
                price = row[BooksTable.price],
                category = row[BooksTable.category],
                imageUrl = row[BooksTable.imageUrl],
                isbn = row[BooksTable.isbn],
                pageCount = row[BooksTable.pageCount],
                publisher = row[BooksTable.publisher],
                publicationYear = row[BooksTable.publicationYear],
                averageRating = row[BooksTable.averageRating],
                stock = row[BooksTable.stock],
                dimensions = row[BooksTable.dimensions],
                weight = row[BooksTable.weight],
                language = row[BooksTable.language],
                format = row[BooksTable.format],
                edition = row[BooksTable.edition],
                genre = row[BooksTable.genre],
                publicationDate = row[BooksTable.publicationDate],
                binding = row[BooksTable.binding],
                tableOfContents = row[BooksTable.tableOfContents],
                awards = row[BooksTable.awards],
                contributors = row[BooksTable.contributors],
                annotations = row[BooksTable.annotations],
                tags = row[BooksTable.tags]
            )
        }
    }
}