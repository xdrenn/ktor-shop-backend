package com.backend.data.remote

import com.backend.data.instruments.GuitarDTO
import kotlinx.serialization.Serializable

@Serializable
data class GuitarRequest(
    val category: String,
    val brand: String,
    val color: String,
    val image: String,
    val price: Double,
    val description: String,
    val name: String
)

@Serializable
data class GuitarCategory(
    val category: String,
)

@Serializable
data class GuitarBrand(
    val brand: String
)
@Serializable
data class GuitarResponse(
    val guitar: List<GuitarDTO?>?
)

@Serializable
data class GuitarId(
    val id: Int
)