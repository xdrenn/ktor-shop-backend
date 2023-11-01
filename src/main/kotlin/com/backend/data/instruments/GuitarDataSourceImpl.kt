package com.backend.data.instruments

import com.backend.db.DatabaseFactory
import com.backend.utils.ServerResponse
import io.ktor.http.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class GuitarDataSourceImpl: GuitarDataSource {


    override suspend fun getGuitarByCategory(category: String): ServerResponse<List<GuitarDTO>> {
        val result = DatabaseFactory.dbQuery {
            ServerResponse.Success(GuitarTable.select(GuitarTable.category eq category).map {
                GuitarTable.resultRowToGuitar(it)
            })
        }
        return if (result.data == null) {
            ServerResponse.Failure(message = "Guitar not found", statusCode = HttpStatusCode.NotFound)
        } else result
    }

    override suspend fun insertNewGuitar(guitarDTO: GuitarDTO): ServerResponse<Boolean> {
        return try {
            val result = DatabaseFactory.dbQuery {
                GuitarTable.insert {
                    it[category] = guitarDTO.category
                    it[brand] = guitarDTO.brand
                    it[image] = guitarDTO.image
                    it[color] = guitarDTO.color
                    it[price] = guitarDTO.price
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

    override suspend fun getGuitarByBrand(brand: String): ServerResponse<List<GuitarDTO>> {
        val result = DatabaseFactory.dbQuery {
            ServerResponse.Success(GuitarTable.select(GuitarTable.brand eq brand).map {
                GuitarTable.resultRowToGuitar(it)
            })
        }
        return if (result.data == null) {
            ServerResponse.Failure(message = "Guitar not found", statusCode = HttpStatusCode.NotFound)
        } else result
    }

    override suspend fun getGuitarById(id: Int): ServerResponse<List<GuitarDTO>> {
        val result = DatabaseFactory.dbQuery {
            ServerResponse.Success(GuitarTable.select(GuitarTable.id eq id).map {
                GuitarTable.resultRowToGuitar(it)
            })
        }
        return if (result.data == null) {
            ServerResponse.Failure(message = "Guitar not found", statusCode = HttpStatusCode.NotFound)
        } else result
    }
}