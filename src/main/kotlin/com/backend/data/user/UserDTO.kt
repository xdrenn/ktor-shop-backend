package com.backend.data.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: Int = 0,
    val login: String,
    val password: String,
    val salt: String
)