package com.backend.domain.usecases

import com.backend.data.remote.UserResponse
import com.backend.data.user.UserDataSource
import com.backend.utils.ServerResponse

class GetUserByLogin(private val userDataSource: UserDataSource) {
    suspend operator fun invoke(login: String): UserResponse {
        return when (val user = userDataSource.getUserByLogin(login)) {
            is ServerResponse.Success -> UserResponse(user.data)
            is ServerResponse.Failure -> UserResponse(null)
        }
    }
}