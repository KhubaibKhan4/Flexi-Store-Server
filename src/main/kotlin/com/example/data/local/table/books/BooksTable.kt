package com.example.data.local.table.books

import org.jetbrains.exposed.sql.Table

object BooksTable: Table("Books") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", length = 500)
    val author = varchar("author", length = 250)
    val description = varchar("description", length = 1000)
    val price = double("price")
    val category = varchar("category", length = 400)
    val imageUrl = varchar("imageUrl", length = 500)
    val isbn = varchar("isbn", length = 50)
    val pageCount = integer("pageCount")
    val publisher = varchar("publisher", length = 400)
    val publicationYear = integer("publicationYear")
    val averageRating = double("averageRating")
    val stock = integer("stock")
    val dimensions = varchar("dimensions", length = 51)
    val weight = double("weight")
    val language = varchar("language", length = 51)
    val format = varchar("format", length = 51)
    val edition = varchar("edition", length = 51)
    val genre = varchar("genre", length = 1000)
    val publicationDate = varchar("publicationDate", length = 51)
    val binding = varchar("binding", length = 51)
    val tableOfContents = varchar("tableOfContents", length = 1000)
    val awards = varchar("awards", length = 100)
    val contributors = varchar("contributors", length = 100)
    val annotations = text("annotations").nullable()
    val tags = varchar("tags", length = 300)

    override val primaryKey = PrimaryKey(id)
}