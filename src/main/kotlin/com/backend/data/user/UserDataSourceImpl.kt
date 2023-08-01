package com.backend.data.user

import com.backend.data.user.UserTable.resultRowToUser
import com.backend.db.DatabaseFactory.dbQuery
import com.backend.utils.ServerResponse
import io.ktor.http.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserDataSourceImpl : UserDataSource {
    override suspend fun getUserByLogin(login: String): ServerResponse<UserDTO> {
        val result = dbQuery {
            UserTable.select(UserTable.login eq login).singleOrNull()
        }
        return if (result == null) {
            ServerResponse.Failure(message = "User not found", statusCode = HttpStatusCode.NotFound)
        }
        else ServerResponse.Success(resultRowToUser(result))
    }

    override suspend fun insertNewUser(userDTO: UserDTO): ServerResponse<Boolean> {
        return try {
            val result = dbQuery {
                UserTable.insert{
                    it[login] = userDTO.login
                    it[password] = userDTO.password
                    it[salt] = userDTO.salt
                }
            }
            ServerResponse.Success(result.insertedCount > 0)
        } catch (e: Exception) {
            e.printStackTrace()
            ServerResponse.Failure()
        }
    }
}
