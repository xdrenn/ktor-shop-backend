package com.backend.data.users

import com.backend.utils.ServerResponse

interface UserDataSource {
    suspend fun getUserByUsername(username: String): ServerResponse<UserDTO>
    suspend fun insertNewUser(userDTO: UserDTO): ServerResponse<Boolean>

    suspend fun isUsernameTaken(username: String): Boolean

    suspend fun deleteUser(id: Int): ServerResponse<Boolean>
}