package com.backend.data.users

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

object UserTable : IntIdTable("users") {
    val username = varchar("username", 30)
    val password = varchar("password", 70)
    val salt = varchar("salt", 70)

    fun resultRowToUser(row: ResultRow): UserDTO {
        return UserDTO(
            id = row[id].value,
            username = row[username],
            password = row[password],
            salt = row[salt]
        )
    }
}