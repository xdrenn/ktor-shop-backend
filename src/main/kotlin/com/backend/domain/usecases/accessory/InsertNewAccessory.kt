package com.backend.domain.usecases.accessory

import com.backend.data.accessories.AccessoriesDTO
import com.backend.data.accessories.AccessoriesDataSource
import com.backend.utils.ServerResponse
import io.ktor.http.*

class InsertNewAccessory(private val accessoriesDataSource: AccessoriesDataSource){

    suspend operator fun invoke(accessory: AccessoriesDTO): Any {
        return when(ValidateAccessory().invoke(accessory)){
            is ServerResponse.Success -> {
                when (accessoriesDataSource.insertNewAccessory(accessory)) {
                    is ServerResponse.Success -> HttpStatusCode.OK
                    is ServerResponse.Failure -> HttpStatusCode.BadRequest
                }
            }
            is ServerResponse.Failure -> HttpStatusCode.BadRequest
        }
    }
}