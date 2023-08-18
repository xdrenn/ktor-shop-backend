package com.backend.domain.usecases.clothes

import com.backend.data.clothes.ClothesDTO
import com.backend.utils.ServerResponse

class ValidateClothes {

    operator fun invoke(clothes: ClothesDTO): ServerResponse<Boolean> {
        return if (clothes.category.isBlank() && clothes.image.isBlank() && clothes.color.isBlank()) {
            ServerResponse.Failure(message = "Fields must not be empty")
        } else ServerResponse.Success()
    }
}