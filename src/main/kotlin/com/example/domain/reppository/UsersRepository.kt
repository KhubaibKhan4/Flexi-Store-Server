package com.example.domain.reppository

import com.example.data.local.DatabaseFactory
import com.example.data.local.table.UserTable
import com.example.data.repository.users.UsersDao
import com.example.domain.model.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement

class UsersRepository : UsersDao {

    private fun rowToResult(row: ResultRow): Users?{
        if (row == null){
            return null
        }
        else{
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
}