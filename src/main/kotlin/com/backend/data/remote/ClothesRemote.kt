package com.backend.data.remote

import com.backend.data.clothes.ClothesDTO
import kotlinx.serialization.Serializable

@Serializable
data class ClothesRequest(
    val category: String,
    val color: String,
    val image: String,
    val description: String
)

@Serializable
data class PieceOfClothes(
    val category: String
)

@Serializable
data class ClothesResponse(
    val clothes: ClothesDTO?
)