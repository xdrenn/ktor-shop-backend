package com.backend.data.accessories

import com.backend.db.DatabaseFactory.dbQuery
import com.backend.utils.ServerResponse
import io.ktor.http.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class AccessoriesDataSourceImpl : AccessoriesDataSource {
    override suspend fun getAccessoryByCategory(category: String): ServerResponse<List<AccessoriesDTO>> {
        val result = dbQuery {
            ServerResponse.Success(AccessoriesTable.select(AccessoriesTable.category eq category).map {
                AccessoriesTable.resultRowToAccessories(it)
            })
        }
        return if (result.data == null) {
            ServerResponse.Failure(message = "Accessory not found", statusCode = HttpStatusCode.NotFound)
        } else result
    }

    override suspend fun getAccessoryBySubcategory(subcategory: String): ServerResponse<List<AccessoriesDTO>> {
        val result = dbQuery {
            ServerResponse.Success(AccessoriesTable.select(AccessoriesTable.subcategory eq subcategory).map {
                AccessoriesTable.resultRowToAccessories(it)
            })
        }
        return if (result.data == null) {
            ServerResponse.Failure(message = "Accessory not found", statusCode = HttpStatusCode.NotFound)
        } else result
    }

    override suspend fun getAccessoryById(id: Int): ServerResponse<List<AccessoriesDTO>> {
        val result = dbQuery {
            ServerResponse.Success(AccessoriesTable.select(AccessoriesTable.id eq id).map {
                AccessoriesTable.resultRowToAccessories(it)
            })
        }
        return if (result.data == null) {
            ServerResponse.Failure(message = "Accessory not found", statusCode = HttpStatusCode.NotFound)
        } else result
    }


    override suspend fun insertNewAccessory(accessoriesDTO: AccessoriesDTO): ServerResponse<Boolean> {
        return try {
            val result = dbQuery {
                AccessoriesTable.insert {
                    it[category] = accessoriesDTO.category
                    it[image] = accessoriesDTO.image
                    it[price] = accessoriesDTO.price
                    it[description] = accessoriesDTO.description
                    it[name] = accessoriesDTO.name
                }
            }
            ServerResponse.Success(result.insertedCount > 0)
        } catch (e: Exception) {
            e.printStackTrace()
            ServerResponse.Failure()
        }
    }
}