package com.backend.data.remote

import com.backend.data.accessories.AccessoriesDTO
import kotlinx.serialization.Serializable

@Serializable
data class AccessoryRequest(
    val category: String,
    val image: String,
    val price: Double,
    val description: String,
    val name: String,
    val subcategory: String
)

@Serializable
data class AccessoryByCategory(
    val category: String
)

@Serializable
data class AccessoryBySubcategory(
    val subcategory: String
)
@Serializable
data class AccessoryById(
    val id: Int
)
@Serializable
data class AccessoryResponse(
    val accessory: List<AccessoriesDTO?>?
)