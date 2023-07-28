package com.backend.hash

data class SaltedHash (
    val hash: String,
    val salt: String
)