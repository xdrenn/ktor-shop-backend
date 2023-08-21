package com.backend.domain.usecases.user

import com.backend.data.remote.UserResponse
import com.backend.data.users.UserDataSource
import com.backend.utils.ServerResponse

class GetUserByUsername(private val userDataSource: UserDataSource) {
    suspend operator fun invoke(username: String): UserResponse {
        return when (val user = userDataSource.getUserByUsername(username)) {
            is ServerResponse.Success -> UserResponse(user.data)
            is ServerResponse.Failure -> UserResponse(null)
        }
    }
}