package com.example.domain.model.login

import com.example.domain.model.user.Users
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val message: String,
    val users: Users
)
