package com.backend.data.instruments


import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

object GuitarTable: IntIdTable("guitars") {
    val category = GuitarTable.varchar("category", 30)
    val brand = GuitarTable.varchar("brand", 30)
    val image = GuitarTable.varchar("image", 100)
    val color = GuitarTable.varchar("color", 30)
    val price = GuitarTable.varchar("price", 30)
    val description = GuitarTable.varchar("description", 1000)
    val name = GuitarTable.varchar("name", 100)

    fun resultRowToClothes(row: ResultRow): GuitarDTO{
        return GuitarDTO(
            id = row[id].value,
            category = row[category],
            brand = row[brand],
            image = row[image],
            color = row[color],
            price = row[price],
            description = row[description],
            name = row[name]
        )
    }
}