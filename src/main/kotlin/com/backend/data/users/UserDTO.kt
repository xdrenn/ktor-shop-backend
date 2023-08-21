package com.backend.data.users

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: Int = 0,
    val username: String,
    val password: String,
    val salt: String
)