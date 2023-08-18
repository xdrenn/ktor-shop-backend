package com.backend.data.clothes

import com.backend.db.DatabaseFactory
import com.backend.utils.ServerResponse
import io.ktor.http.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class ClothesDataSourceImpl: ClothesDataSource {

    override suspend fun getClothesByCategory(category: String): ServerResponse<ClothesDTO> {
        val result = DatabaseFactory.dbQuery {
            ClothesTable.select(ClothesTable.category eq category).singleOrNull()
        }
        return if (result == null) {
            ServerResponse.Failure(message = "Clothes not found", statusCode = HttpStatusCode.NotFound)
        }
        else ServerResponse.Success(ClothesTable.resultRowToClothes(result))
    }

    override suspend fun insertNewClothes(clothesDTO: ClothesDTO): ServerResponse<Boolean> {
        return try {
            val result = DatabaseFactory.dbQuery {
                ClothesTable.insert {
                    it[category] = clothesDTO.category
                    it[image] = clothesDTO.image
                    it[color] = clothesDTO.color
                    it[description] = clothesDTO.description
                }
            }
            ServerResponse.Success(result.insertedCount > 0)
        } catch (e: Exception) {
            e.printStackTrace()
            ServerResponse.Failure()
        }
    }
}