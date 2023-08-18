package com.backend.data.clothes

import kotlinx.serialization.Serializable

@Serializable
data class ClothesDTO (
    val id: Int = 0,
    val category: String,
    val image: String,
    val description: String,
    val color: String
)