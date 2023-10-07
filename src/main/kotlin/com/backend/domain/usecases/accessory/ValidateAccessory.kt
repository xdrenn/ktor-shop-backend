package com.backend.domain.usecases.accessory

import com.backend.data.accessories.AccessoriesDTO
import com.backend.utils.ServerResponse

class ValidateAccessory {

    operator fun invoke(accessory: AccessoriesDTO): ServerResponse<Boolean> {
        return if (accessory.category.isBlank() && accessory.image.isBlank()) {
            ServerResponse.Failure(message = "Fields must not be empty")
        } else ServerResponse.Success()
    }
}