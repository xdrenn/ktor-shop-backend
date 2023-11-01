package com.backend.data.accessories

import kotlinx.serialization.Serializable

@Serializable
data class AccessoriesDTO (
    val id: Int = 0,
    val category: String,
    val price: Double,
    val description: String,
    val image: String,
    val name: String,
    val subcategory: String
)