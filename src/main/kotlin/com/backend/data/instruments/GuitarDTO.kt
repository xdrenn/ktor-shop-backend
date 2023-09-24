package com.backend.data.instruments

import kotlinx.serialization.Serializable

@Serializable
data class GuitarDTO (
    val id: Int = 0,
    val category: String,
    val brand: String,
    val image: String,
    val price: String,
    val description: String,
    val color: String
)