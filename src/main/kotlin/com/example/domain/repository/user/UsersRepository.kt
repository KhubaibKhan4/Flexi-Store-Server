package com.example.domain.repository.user

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
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
import java.security.MessageDigest
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
                postalCode = row[UserTable.postalCode],
                phoneNumber = row[UserTable.phoneNumber],
                userRole = row[UserTable.userRole],
                profileImage = row[UserTable.profileImage]
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
        postalCode: Long,
        phoneNumber: String,
        userRole: String,
        profileImage: String
    ): Users? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = UserTable.insert { users ->
                users[UserTable.username] = username
                users[UserTable.email] = email
                users[UserTable.password] = hashPassword(password)
                users[UserTable.fullName] = fullName
                users[UserTable.address] = address
                users[UserTable.city] = city
                users[UserTable.country] = country
                users[UserTable.postalCode] = postalCode
                users[UserTable.phoneNumber] = phoneNumber
                users[UserTable.userRole] = userRole
                users[UserTable.profileImage] = profileImage
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
                if (verifyPassword(password, storedPassword)) {
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
        postalCode: Long,
        country: String,
        phoneNumber: String,
        profileImage: String
    ): Int =
        DatabaseFactory.dbQuery {
            UserTable.update({ UserTable.id.eq(id) }) { user ->
                user[UserTable.id] = id
                user[UserTable.username] = username
                user[UserTable.email] = email
                user[UserTable.password] = hashPassword(password)
                user[UserTable.fullName] = fullName
                user[UserTable.address] = address
                user[UserTable.city] = city
                user[UserTable.country]= country
                user[UserTable.postalCode] = postalCode
                user[UserTable.phoneNumber] = phoneNumber
                user[UserTable.profileImage]= profileImage
            }
        }

    override suspend fun updateAddress(
        id: Long,
        address: String,
        city: String,
        country: String,
        postalCode: Long
    ): Int =
        DatabaseFactory.dbQuery {
            UserTable.update({ UserTable.id.eq(id) }) { user ->
                user[UserTable.address] = address
                user[UserTable.city] = city
                user[UserTable.country] = country
                user[UserTable.postalCode] = postalCode
            }
        }

    override suspend fun updateProfile(id: Long, profileImage: String): Int =
        DatabaseFactory.dbQuery {
            UserTable.update({UserTable.id.eq(id)}){user->
                user[UserTable.id] = id
                user[UserTable.profileImage] = profileImage
            }
        }


    private val jwtVerifier : JWTVerifier = JWT.require(Algorithm.HMAC256(jwtSecret))
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .build()
    private fun generateJwtToken(password: String): String {
        val expirationTimeMillis = System.currentTimeMillis() + 3600 * 1000
        return JWT.create()
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withSubject(password)
            .withExpiresAt(Date(expirationTimeMillis))
            .sign(Algorithm.HMAC256(jwtSecret))
    }

    fun validateJwtToken(token: String): String? {
        return try {
            val payload: Payload = jwtVerifier.verify(token)
            payload.subject
        } catch (e: TokenExpiredException) {
            println("Token has expired: ${e.message}")
            null
        } catch (e: JWTVerificationException) {
            println("JWT verification failed: ${e.message}")
            null
        }
    }
    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(password.toByteArray(Charsets.UTF_8))
        return hashedBytes.joinToString("") { "%02x".format(it) }
    }

    private fun verifyPassword(providedPassword: String, hashedPassword: String): Boolean {
        val hashedProvidedPassword = hashPassword(providedPassword)
        return hashedProvidedPassword == hashedPassword
    }


}