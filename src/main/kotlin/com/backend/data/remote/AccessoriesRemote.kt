package com.backend.data.remote

import com.backend.data.accessories.AccessoriesDTO
import kotlinx.serialization.Serializable

@Serializable
data class AccessoryRequest(
    val category: String,
    val image: String,
    val price: String,
    val description: String,
    val name: String
)

@Serializable
data class Accessory(
    val category: String
)
@Serializable
data class AccessoryResponse(
    val accessory: AccessoriesDTO?
)