package com.example.data.repository.users

import com.example.domain.model.user.Users

interface UsersDao {
    suspend fun insert(
        username: String,
        email: String,
        password: String,
        fullName: String,
        address: String,
        city: String,
        country: String,
        phoneNumber: String,
        userRole: String
    ): Users?
    suspend fun login(
        email: String,
        password: String
    ):Users?

    suspend fun getAllUsers(): List<Users>?
    suspend fun getUserById(id: Long): Users?
    suspend fun deleteUserById(id: Long): Int
    suspend fun updateUsers(
        id: Long,
        username: String,
        email: String,
        password: String,
        fullName: String,
        address: String,
        city: String,
        country: String,
        phoneNumber: String,
    ): Int
}