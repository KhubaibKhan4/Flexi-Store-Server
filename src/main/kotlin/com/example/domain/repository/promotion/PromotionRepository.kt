package com.example.domain.repository.promotion

import com.example.data.local.table.db.DatabaseFactory
import com.example.data.local.table.promotion.PromotionTable
import com.example.data.repository.promotion.PromotionDao
import com.example.domain.model.promotion.Promotion
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement

class PromotionRepository : PromotionDao {
    override suspend fun insert(
        title: String,
        description: String,
        imageUrl: String,
        startDate: Long,
        endDate: Long,
        enable: Boolean
    ): Promotion? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = PromotionTable.insert { promotion ->
                promotion[PromotionTable.title] = title
                promotion[PromotionTable.description] = description
                promotion[PromotionTable.imageUrl] = imageUrl
                promotion[PromotionTable.startDate] = startDate
                promotion[PromotionTable.endDate] = endDate
                promotion[PromotionTable.enabled] = enable
            }
        }
        return rowToResult(statement?.resultedValues?.get(0)!!)
    }

    override suspend fun getPromotionsList(): List<Promotion>? {
        return DatabaseFactory.dbQuery {
            PromotionTable.selectAll()
                .mapNotNull {
                    rowToResult(it)
                }
        }
    }

    override suspend fun getPromotionById(id: Long): Promotion? {
        return DatabaseFactory.dbQuery {
            PromotionTable.select { PromotionTable.id.eq(id) }
                .map {
                    rowToResult(it)
                }
                .single()
        }
    }

    override suspend fun deletePromotionById(id: Long): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun updatePromotion(
        id: Long,
        title: String,
        description: String,
        imageUrl: String,
        startDate: Long,
        endDate: Long,
        enable: Boolean
    ) {
        TODO("Not yet implemented")
    }

    private fun rowToResult(row: ResultRow): Promotion? {
        if (row == null) {
            return null
        } else {
            return Promotion(
                id = row[PromotionTable.id],
                title = row[PromotionTable.title],
                description = row[PromotionTable.description],
                imageUrl = row[PromotionTable.imageUrl],
                startDate = row[PromotionTable.startDate],
                endDate = row[PromotionTable.endDate],
                enabled = row[PromotionTable.enabled]
            )
        }
    }

}