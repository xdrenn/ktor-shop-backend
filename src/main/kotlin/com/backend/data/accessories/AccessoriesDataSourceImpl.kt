package com.backend.data.accessories

import com.backend.db.DatabaseFactory.dbQuery
import com.backend.utils.ServerResponse
import io.ktor.http.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class AccessoriesDataSourceImpl : AccessoriesDataSource {
    override suspend fun getAccessoryByCategory(category: String): ServerResponse<AccessoriesDTO> {
        val result = dbQuery {
            AccessoriesTable.select(AccessoriesTable.category eq category).singleOrNull()
        }
        return if (result == null) {
            ServerResponse.Failure(message = "Accessory not found", statusCode = HttpStatusCode.NotFound)
        } else ServerResponse.Success(AccessoriesTable.resultRowToAccessories(result))
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