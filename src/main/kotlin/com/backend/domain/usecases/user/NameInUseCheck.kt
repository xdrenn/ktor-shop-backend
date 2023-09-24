package com.backend.domain.usecases.user

import com.backend.data.users.UserDataSource

class NameInUseCheck(private val userDataSource: UserDataSource) {
    suspend operator fun invoke(username: String): Boolean {
        return userDataSource.isUsernameTaken(username)
    }
}