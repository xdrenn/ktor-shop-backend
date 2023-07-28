package com.backend.data.user

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

object UserTable : IntIdTable("users") {
    val login = varchar("login", 30)
    val password = varchar("password", 70)
    val salt = varchar("salt", 70)

    fun resultRowToUser(row: ResultRow): UserDTO {
        return UserDTO(
            id = row[id].value,
            login = row[login],
            password = row[password],
            salt = row[salt]
        )
    }
}