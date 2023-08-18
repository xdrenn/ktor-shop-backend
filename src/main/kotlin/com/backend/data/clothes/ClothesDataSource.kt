package com.backend.data.clothes

import com.backend.utils.ServerResponse

interface ClothesDataSource {
    suspend fun getClothesByCategory(category: String): ServerResponse<ClothesDTO>
    suspend fun insertNewClothes(clothesDTO: ClothesDTO): ServerResponse<Boolean>
}