package com.example.data.repository.users

import com.example.domain.model.Users

interface UsersDao {
    suspend fun insert(
        username: String,
        email: String,
        password: String,
    ): Users?
}