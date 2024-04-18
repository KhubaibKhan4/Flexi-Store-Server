package com.example.data.repository.books

interface BooksDao {
    suspend fun insert(
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
    ): BooksDao?

    suspend fun getAllBooks(): List<BooksDao>?
    suspend fun getBookById(id: Long): BooksDao?
    suspend fun deleteBookById(id: Long): Int?
    suspend fun updateBookById(
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
    ): Int?
}
