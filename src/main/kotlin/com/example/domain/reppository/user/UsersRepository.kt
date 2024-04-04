package com.example.domain.reppository.user

import com.example.data.local.table.db.DatabaseFactory
import com.example.data.local.table.user.UserTable
import com.example.data.repository.users.UsersDao
import com.example.domain.model.user.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement

class UsersRepository : UsersDao {

    private fun rowToResult(row: ResultRow): Users? {
        if (row == null) {
            return null
        } else {
            return Users(
                id = row[UserTable.id],
                username = row[UserTable.username],
                email = row[UserTable.email],
                password = row[UserTable.password]
            )
        }
    }

    override suspend fun insert(username: String, email: String, password: String): Users? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = UserTable.insert { users ->
                users[UserTable.username] = username
                users[UserTable.email] = email
                users[UserTable.password] = password
            }
        }
        return rowToResult(statement?.resultedValues?.get(0)!!)
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

    override suspend fun updateUsers(id: Long, username: String, email: String, password: String): Int =
        DatabaseFactory.dbQuery {
            UserTable.update({ UserTable.id.eq(id) }) { user ->
                user[UserTable.id] = id
                user[UserTable.username] = username
                user[UserTable.email] = email
                user[UserTable.password] = password
            }
        }

}