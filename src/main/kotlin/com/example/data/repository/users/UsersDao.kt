package com.example.data.repository.users

interface UsersDao {
    suspend fun insert(
        username: String,
        email: String,
        password: String,
    )
}