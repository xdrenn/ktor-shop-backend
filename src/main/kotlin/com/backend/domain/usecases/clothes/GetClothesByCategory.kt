package com.backend.domain.usecases.clothes

import com.backend.data.clothes.ClothesDataSource
import com.backend.data.remote.ClothesResponse
import com.backend.utils.ServerResponse

class GetClothesByCategory(private val clothesDataSource: ClothesDataSource) {
    suspend operator fun invoke(category: String): ClothesResponse {
        return when (val clothes = clothesDataSource.getClothesByCategory(category)) {
            is ServerResponse.Success -> ClothesResponse(clothes.data)
            is ServerResponse.Failure -> ClothesResponse(null)
        }
    }
}