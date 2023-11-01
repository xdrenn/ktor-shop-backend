package com.backend.data.users

import com.backend.data.users.UserTable.resultRowToUser
import com.backend.db.DatabaseFactory.dbQuery
import com.backend.utils.ServerResponse
import io.ktor.http.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserDataSourceImpl : UserDataSource {
    override suspend fun getUserByUsername(username: String): ServerResponse<UserDTO> {
        val result = dbQuery {
            UserTable.select(UserTable.username eq username).singleOrNull()
        }
        return if (result == null) {
            ServerResponse.Failure(message = "User not found", statusCode = HttpStatusCode.NotFound)
        } else ServerResponse.Success(resultRowToUser(result))
    }

    override suspend fun insertNewUser(userDTO: UserDTO): ServerResponse<Boolean> {
        return try {
            val result = dbQuery {
                UserTable.insert {
                    it[username] = userDTO.username
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

    override suspend fun isUsernameTaken(username: String): Boolean {
        val result = dbQuery {
          UserTable.select(UserTable.username eq username).singleOrNull()
        }
        return result == null
    }

    override suspend fun deleteUser(id: Int): ServerResponse<Boolean> {
        return try {
            dbQuery {
                UserTable.deleteWhere { UserTable.id eq id }
            }
            ServerResponse.Success()
        } catch (e: Exception) {
            e.printStackTrace()
            ServerResponse.Failure()
        }
    }
}
