package com.backend.data.remote

import com.backend.data.users.UserDTO
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)


@Serializable
data class LoginResponse(
    val token: String
)

@Serializable
data class UserResponse(val user: UserDTO?)