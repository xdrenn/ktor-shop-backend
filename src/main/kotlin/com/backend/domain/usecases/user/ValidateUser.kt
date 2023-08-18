package com.backend.domain.usecases.user

import com.backend.data.users.UserDTO
import com.backend.utils.ServerResponse

class ValidateUser {
    operator fun invoke(user: UserDTO): ServerResponse<Boolean> {
        return if (user.login.isBlank() && user.password.isBlank()) {
            ServerResponse.Failure(message = "Fields must not be empty")
        } else ServerResponse.Success()
    }
}