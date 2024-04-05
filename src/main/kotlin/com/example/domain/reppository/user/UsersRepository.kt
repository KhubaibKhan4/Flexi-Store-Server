package com.example.domain.reppository.user

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.Payload
import com.example.data.local.table.db.DatabaseFactory
import com.example.data.local.table.user.UserTable
import com.example.data.repository.users.UsersDao
import com.example.domain.model.user.Users
import org.h2.engine.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UsersRepository : UsersDao {
    private val jwtSecret: String= "sectret"
    private val jwtAudience: String = "jwtAudience"
    private val jwtIssuer: String ="jwtIssuer"
    private fun rowToResult(row: ResultRow): Users? {
        if (row == null) {
            return null
        } else {
            return Users(
                id = row[UserTable.id],
                username = row[UserTable.username],
                email = row[UserTable.email],
                password = row[UserTable.password],
                fullName = row[UserTable.fullName],
                address = row[UserTable.address],
                city = row[UserTable.city],
                country = row[UserTable.country],
                phoneNumber = row[UserTable.phoneNumber],
                userRole = row[UserTable.userRole]
            )
        }
    }


    override suspend fun insert(
        username: String,
        email: String,
        password: String,
        fullName: String,
        address: String,
        city: String,
        country: String,
        phoneNumber: String,
        userRole: String
    ): Users? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = UserTable.insert { users ->
                users[UserTable.username] = username
                users[UserTable.email] = email
                users[UserTable.password] = generateJwtToken(password)
                users[UserTable.fullName] = fullName
                users[UserTable.address] = address
                users[UserTable.city] = city
                users[UserTable.country] = country
                users[UserTable.phoneNumber] = phoneNumber
                users[UserTable.userRole] = userRole
            }
        }
        return rowToResult(statement?.resultedValues?.get(0)!!)
    }

    override suspend fun login(email: String, password: String): Users? {
        var user: Users? = null
        transaction {
            val result = UserTable.select { UserTable.email eq email }.singleOrNull()
            result?.let { row ->
                val storedPassword = row[UserTable.password]
                val decodedPassword = validateJwtToken(storedPassword)
                if (decodedPassword == password) {
                    user = rowToResult(row)
                }
            }
        }
        return user
    }

    override suspend fun getAllUsers(): List<Users>? =
        DatabaseFactory.dbQuery {
            UserTable.selectAll().mapNotNull {
                rowToResult(it)
            }
        }

    override suspend fun getUserById(id: Long): Users? =
        DatabaseFactory.dbQuery {
            UserTable.select { UserTable.id.eq(id) }
                .map {
                    rowToResult(it)
                }.singleOrNull()
        }

    override suspend fun deleteUserById(id: Long): Int =
        DatabaseFactory.dbQuery {
            UserTable.deleteWhere { UserTable.id.eq(id) }
        }

    override suspend fun updateUsers(
        id: Long,
        username: String,
        email: String,
        password: String,
        fullName: String,
        address: String,
        city: String,
        country: String,
        phoneNumber: String
    ): Int =
        DatabaseFactory.dbQuery {
            UserTable.update({ UserTable.id.eq(id) }) { user ->
                user[UserTable.id] = id
                user[UserTable.username] = username
                user[UserTable.email] = email
                user[UserTable.password] = generateJwtToken(password)
                user[UserTable.fullName] = fullName
                user[UserTable.address] = address
                user[UserTable.city] = city
                user[UserTable.country]= country
                user[UserTable.phoneNumber] = phoneNumber
            }
        }

    private val jwtVerifier : JWTVerifier = JWT.require(Algorithm.HMAC256(jwtSecret))
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .build()
    private fun generateJwtToken(password: String): String{
        return JWT.create()
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withSubject(password)
            .withExpiresAt(Date(System.currentTimeMillis() + 360000))
            .sign(Algorithm.HMAC256(jwtSecret))
    }
    fun validateJwtToken(token: String): String?{
        return try {
            val payload : Payload = jwtVerifier.verify(token)
            payload.subject
        }catch (e: JWTVerificationException){
            null
        }
    }

}