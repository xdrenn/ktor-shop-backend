package com.backend.domain.usecases.user

import com.backend.data.users.UserDataSource
import com.backend.utils.ServerResponse

class DeleteUser (private val userDataSource: UserDataSource) {

    suspend operator fun invoke(id: Int): ServerResponse<Boolean> {
        return userDataSource.deleteUser(id)
    }
}