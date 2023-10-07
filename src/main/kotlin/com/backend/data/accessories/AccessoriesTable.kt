package com.backend.data.accessories


import com.backend.data.instruments.GuitarTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

object AccessoriesTable: IntIdTable("accessories") {
    val category = AccessoriesTable.varchar("category", 30)
    val price = AccessoriesTable.varchar("price", 30)
    val description = AccessoriesTable.varchar("description", 1000)
    val image = AccessoriesTable.varchar("image", 100)
    val name = AccessoriesTable.varchar("name", 100)

    fun resultRowToAccessories(row: ResultRow): AccessoriesDTO {
        return AccessoriesDTO(
            id = row[GuitarTable.id].value,
            category = row[category],
            price = row[price],
            description = row[description],
            image = row[image],
            name = row[name]
        )
    }
}