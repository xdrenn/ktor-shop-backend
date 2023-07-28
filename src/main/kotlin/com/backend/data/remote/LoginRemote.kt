package com.backend.data.remote

import com.backend.data.user.UserDTO
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val login: String,
    val password: String
)


@Serializable
data class LoginResponse(
    val token: String
)

@Serializable
data class UserResponse(val user: UserDTO?)