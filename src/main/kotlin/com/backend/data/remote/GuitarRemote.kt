package com.backend.data.remote

import com.backend.data.instruments.GuitarDTO
import kotlinx.serialization.Serializable

@Serializable
data class GuitarRequest(
    val category: String,
    val brand: String,
    val color: String,
    val image: String,
    val price: String,
    val description: String
)

@Serializable
data class Guitar(
    val category: String,
    val brand: String
)

@Serializable
data class GuitarResponse(
    val guitar: GuitarDTO?
)