package com.backend.data.instruments

import com.backend.utils.ServerResponse

interface GuitarDataSource {
    suspend fun getGuitarByCategory(category: String): ServerResponse<GuitarDTO>
    suspend fun insertNewGuitar(guitarDTO: GuitarDTO): ServerResponse<Boolean>

    suspend fun getGuitarByBrand(brand: String): ServerResponse<GuitarDTO>
}