package com.backend.controller

import com.backend.data.instruments.GuitarDTO
import com.backend.data.instruments.GuitarDataSource
import com.backend.data.remote.*
import com.backend.domain.usecases.guitar.GetGuitarByBrand
import com.backend.domain.usecases.guitar.GetGuitarByCategory
import com.backend.domain.usecases.guitar.GetGuitarById
import com.backend.domain.usecases.guitar.InsertNewGuitar
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class GuitarsController(
    private val guitarDataSource: GuitarDataSource
) {
    suspend fun getGuitarByCategory(call: ApplicationCall) {

        val request = call.receiveNullable<GuitarCategory>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return
        }

        val guitar = GetGuitarByCategory(guitarDataSource).invoke(request.category).guitar
        if (guitar == null) {
            call.respond(HttpStatusCode.NotFound, "Guitars not found")
            return
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = GuitarResponse(
                guitar = guitar
            )
        )
    }

    suspend fun getGuitarByBrand(call: ApplicationCall) {

        val request = call.receiveNullable<GuitarBrand>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return
        }

        val guitar = GetGuitarByBrand(guitarDataSource).invoke(request.brand).guitar
        if (guitar == null) {
            call.respond(HttpStatusCode.NotFound, "Guitars not found")
            return
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = GuitarResponse(
                guitar = guitar
            )
        )
    }

    suspend fun getGuitarById(call: ApplicationCall){

        val request = call.receiveNullable<GuitarId>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return
        }

        val guitar = GetGuitarById(guitarDataSource).invoke(request.id).guitar
        if (guitar == null) {
            call.respond(HttpStatusCode.NotFound, "Guitars not found")
            return
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = GuitarResponse(
                guitar = guitar
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
                description = request.description,
                name = request.name
            )
        )
        call.respond(HttpStatusCode.OK)
    }
}