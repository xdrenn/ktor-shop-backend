package com.backend.security

data class TokenConfig(
    val issuer: String,
    val audience: String,
    val expires: Long,
    val secret: String
)