package com.backend.data.clothes


import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

object ClothesTable: IntIdTable("clothes") {
    val category = ClothesTable.varchar("category", 30)
    val image = ClothesTable.varchar("image", 100)
    val color = ClothesTable.varchar("color", 30)
    val description = ClothesTable.varchar("description", 200)

    fun resultRowToClothes(row: ResultRow): ClothesDTO{
        return ClothesDTO(
            id = row[id].value,
            category = row[category],
            image = row[image],
            color = row[color],
            description = row[description]
        )
    }
}