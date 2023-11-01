package com.backend.controller

import com.backend.data.accessories.AccessoriesDTO
import com.backend.data.accessories.AccessoriesDataSource
import com.backend.data.remote.*
import com.backend.domain.usecases.accessory.GetAccessoryByCategory
import com.backend.domain.usecases.accessory.GetAccessoryById
import com.backend.domain.usecases.accessory.GetAccessoryBySubcategory
import com.backend.domain.usecases.accessory.InsertNewAccessory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class AccessoriesController(
    private val accessoriesDataSource: AccessoriesDataSource
) {
    suspend fun getAccessoryByCategory(call: ApplicationCall) {

        val request = call.receiveNullable<AccessoryByCategory>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return
        }

        val accessory = GetAccessoryByCategory(accessoriesDataSource).invoke(request.category).accessory
        if (accessory == null) {
            call.respond(HttpStatusCode.NotFound, "Accessory not found")
            return
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = AccessoryResponse(
                accessory = accessory
            )
        )
    }

    suspend fun getAccessoryBySubcategory(call: ApplicationCall) {

        val request = call.receiveNullable<AccessoryBySubcategory>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return
        }

        val accessory = GetAccessoryBySubcategory(accessoriesDataSource).invoke(request.subcategory).accessory
        if (accessory == null) {
            call.respond(HttpStatusCode.NotFound, "Accessory not found")
            return
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = AccessoryResponse(
                accessory = accessory
            )
        )
    }

    suspend fun getAccessoryById(call: ApplicationCall){
        val request = call.receiveNullable<AccessoryById>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return
        }

        val accessory = GetAccessoryById(accessoriesDataSource).invoke(request.id).accessory
        if (accessory == null) {
            call.respond(HttpStatusCode.NotFound, "Accessory not found")
            return
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = AccessoryResponse(
                accessory = accessory
            )
        )
    }


    suspend fun postAccessory(call: ApplicationCall) {
        val request = call.receiveNullable<AccessoryRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return
        }

        val areFieldsEmpty = request.category.isBlank() || request.image.isBlank()
        if (areFieldsEmpty) {
            call.respond(HttpStatusCode.BadRequest, "Fields must not be empty")
            return
        }


        InsertNewAccessory(accessoriesDataSource).invoke(
            AccessoriesDTO(
                category = request.category,
                image = request.image,
                price = request.price,
                description = request.description,
                name = request.name,
                subcategory = request.subcategory
            )
        )
        call.respond(HttpStatusCode.OK)
    }
}