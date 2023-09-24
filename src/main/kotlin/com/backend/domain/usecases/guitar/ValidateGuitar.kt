package com.backend.domain.usecases.guitar

import com.backend.data.instruments.GuitarDTO
import com.backend.utils.ServerResponse

class ValidateGuitar {

    operator fun invoke(guitar: GuitarDTO): ServerResponse<Boolean> {
        return if (guitar.category.isBlank() && guitar.image.isBlank() && guitar.color.isBlank()) {
            ServerResponse.Failure(message = "Fields must not be empty")
        } else ServerResponse.Success()
    }
}