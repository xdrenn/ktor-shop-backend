package com.backend.controller

import com.backend.data.instruments.GuitarDTO
import com.backend.data.instruments.GuitarDataSource
import com.backend.data.remote.Guitar
import com.backend.data.remote.GuitarRequest
import com.backend.data.remote.GuitarResponse
import com.backend.domain.usecases.guitar.GetGuitarByBrand
import com.backend.domain.usecases.guitar.GetGuitarByCategory
import com.backend.domain.usecases.guitar.InsertNewGuitar
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class GuitarsController(
    private val guitarDataSource: GuitarDataSource
) {
    suspend fun getGuitars(call: ApplicationCall) {

        val request = call.receiveNullable<Guitar>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return
        }

        val guitarByCategory = GetGuitarByCategory(guitarDataSource).invoke(request.category).guitar
        if (guitarByCategory == null) {
            call.respond(HttpStatusCode.NotFound, "Guitar not found")
            return
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = GuitarResponse(
                guitar = guitarByCategory
            )
        )

        val guitarByBrand = GetGuitarByBrand(guitarDataSource).invoke(request.brand).guitar
        if (guitarByBrand == null) {
            call.respond(HttpStatusCode.NotFound, "Guitar not found")
            return
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = GuitarResponse(
                guitar = guitarByCategory
            )
        )
    }

    suspend fun postGuitar(call: ApplicationCall) {
        val request = call.receiveNullable<GuitarRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return
        }

        val areFieldsEmpty = request.category.isBlank() || request.color.isBlank() || request.image.isBlank()
        if (areFieldsEmpty) {
            call.respond(HttpStatusCode.BadRequest, "Fields must not be empty")
            return
        }


        InsertNewGuitar(guitarDataSource).invoke(
            GuitarDTO(
                category = request.category,
                brand = request.brand,
                image = request.image,
                color = request.color,
                price = request.price,
                description = request.description
            )
        )
        call.respond(HttpStatusCode.OK)
    }
}