package com.backend.controller

import com.backend.data.clothes.ClothesDTO
import com.backend.data.clothes.ClothesDataSource
import com.backend.data.remote.ClothesRequest
import com.backend.data.remote.ClothesResponse
import com.backend.data.remote.PieceOfClothes
import com.backend.domain.usecases.clothes.GetClothesByCategory
import com.backend.domain.usecases.clothes.InsertNewClothes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class ClothesController(
    private val clothesDataSource: ClothesDataSource
) {
    suspend fun getClothes(call: ApplicationCall) {

        val request = call.receiveNullable<PieceOfClothes>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return
        }

        val clothes = GetClothesByCategory(clothesDataSource).invoke(request.category).clothes
        if (clothes == null) {
            call.respond(HttpStatusCode.NotFound, "Clothes not found")
            return
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = ClothesResponse(
                clothes = clothes
            )
        )
    }

    suspend fun postClothes(call: ApplicationCall) {
        val request = call.receiveNullable<ClothesRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return
        }

        val areFieldsEmpty = request.category.isBlank() || request.color.isBlank() || request.image.isBlank()
        if (areFieldsEmpty) {
            call.respond(HttpStatusCode.BadRequest, "Fields must not be empty")
            return
        }

        InsertNewClothes(clothesDataSource).invoke(
            ClothesDTO(
                category = request.category,
                image = request.image,
                color = request.color,
                description = request.description
            )
        )
    }
}