package com.backend.security

data class TokenConfig(
    val issuer: String = System.getenv("ISSUER"),
    val audience: String = System.getenv("ISSUER"),
    val expires: Long = 365L * 1000L * 60L * 60L * 24L,
    val secret: String = System.getenv("JWT_SECRET")
)