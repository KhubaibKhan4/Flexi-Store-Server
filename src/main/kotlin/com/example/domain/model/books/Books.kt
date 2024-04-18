package com.example.domain.model.books

import kotlinx.serialization.Serializable

@Serializable
data class Books(
    val id: Int,
    val title: String,
    val author: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String,
    val isbn: String,
    val pageCount: Int,
    val publisher: String,
    val publicationYear: Int,
    val averageRating: Double,
    val stock: Int,
    val dimensions: String,
    val weight: Double,
    val language: String,
    val format: String,
    val edition: String,
    val genre: List<String>,
    val publicationDate: String,
    val binding: String,
    val tableOfContents: List<String>,
    val awards: String,
    val contributors: String,
    val annotations: String?,
    val tags: String
)
