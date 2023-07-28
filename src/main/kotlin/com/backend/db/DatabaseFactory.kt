package com.backend.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init(){
        Database.connect(
            url ="jdbc:postgresql://localhost:5432/shopdb",
            driver = "org.postgresql.Driver",
            user = System.getenv("POSTGRES_USER"),
            password = System.getenv("POSTGRES_PASSWORD"))
    }

    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO){
        transaction {
            block()
        }
    }
}