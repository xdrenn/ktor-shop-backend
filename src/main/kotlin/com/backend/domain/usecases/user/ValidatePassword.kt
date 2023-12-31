package com.backend.domain.usecases.user

import com.backend.data.remote.LoginRequest
import com.backend.data.users.UserDTO
import com.backend.hash.HashingService
import com.backend.hash.SaltedHash

class ValidatePassword(private val hashingService: HashingService, private val loginRequest: LoginRequest) {

    operator fun invoke(user: UserDTO): Boolean {
        return hashingService.verify(loginRequest.password, SaltedHash(user.password, user.salt))
    }
}