package com.backend.data.instruments

import com.backend.db.DatabaseFactory
import com.backend.utils.ServerResponse
import io.ktor.http.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class GuitarDataSourceImpl: GuitarDataSource {

    override suspend fun getGuitarByCategory(category: String): ServerResponse<GuitarDTO> {
        val result = DatabaseFactory.dbQuery {
            GuitarTable.select(GuitarTable.category eq category).singleOrNull()
        }
        return if (result == null) {
            ServerResponse.Failure(message = "Guitar not found", statusCode = HttpStatusCode.NotFound)
        }
        else ServerResponse.Success(GuitarTable.resultRowToClothes(result))
    }

    override suspend fun insertNewGuitar(guitarDTO: GuitarDTO): ServerResponse<Boolean> {
        return try {
            val result = DatabaseFactory.dbQuery {
                GuitarTable.insert {
                    it[category] = guitarDTO.category
                    it[image] = guitarDTO.image
                    it[color] = guitarDTO.color
                    it[description] = guitarDTO.description
                    it[name] = guitarDTO.name
                }
            }
            ServerResponse.Success(result.insertedCount > 0)
        } catch (e: Exception) {
            e.printStackTrace()
            ServerResponse.Failure()
        }
    }

    override suspend fun getGuitarByBrand(brand: String): ServerResponse<GuitarDTO> {
        val result = DatabaseFactory.dbQuery {
            GuitarTable.select(GuitarTable.brand eq brand).singleOrNull()
        }
        return if (result == null) {
            ServerResponse.Failure(message = "Guitar not found", statusCode = HttpStatusCode.NotFound)
        }
        else ServerResponse.Success(GuitarTable.resultRowToClothes(result))
    }
}