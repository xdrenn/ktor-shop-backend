package com.backend.domain.usecases.user

import com.backend.data.users.UserDTO
import com.backend.data.users.UserDataSource
import com.backend.utils.ServerResponse
import io.ktor.http.*

class InsertNewUser(private val userDataSource: UserDataSource) {
    suspend operator fun invoke(user: UserDTO): Any {
        return when (ValidateUser().invoke(user)) {
            is ServerResponse.Success -> {
                when (userDataSource.insertNewUser(user)) {
                    is ServerResponse.Success -> HttpStatusCode.OK
                    is ServerResponse.Failure -> HttpStatusCode.BadRequest
                }
            }
            is ServerResponse.Failure -> HttpStatusCode.BadRequest
        }
    }
}