package com.backend.domain.usecases.clothes

import com.backend.data.clothes.ClothesDTO
import com.backend.data.clothes.ClothesDataSource
import com.backend.utils.ServerResponse
import io.ktor.http.*

class InsertNewClothes(private val clothesDataSource: ClothesDataSource){

    suspend operator fun invoke(clothes: ClothesDTO): Any? {
        return when(ValidateClothes().invoke(clothes)){
            is ServerResponse.Success -> {
                when (clothesDataSource.insertNewClothes(clothes)) {
                    is ServerResponse.Success -> null
                    is ServerResponse.Failure -> HttpStatusCode.BadRequest
                }
            }
            is ServerResponse.Failure -> HttpStatusCode.BadRequest
        }
    }
}
