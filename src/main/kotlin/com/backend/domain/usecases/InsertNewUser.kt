package com.backend.domain.usecases

import com.backend.data.user.UserDTO
import com.backend.data.user.UserDataSource
import com.backend.utils.ServerResponse
import io.ktor.http.*

class InsertNewUser(private val userDataSource: UserDataSource) {
    suspend operator fun invoke(user: UserDTO): Any? {
        return when (ValidateUser().invoke(user)) {
            is ServerResponse.Success -> {
                when (userDataSource.insertNewUser(user)) {
                    is ServerResponse.Success -> null
                    is ServerResponse.Failure -> HttpStatusCode.BadRequest
                }
            }
            is ServerResponse.Failure -> HttpStatusCode.BadRequest
        }
    }
}