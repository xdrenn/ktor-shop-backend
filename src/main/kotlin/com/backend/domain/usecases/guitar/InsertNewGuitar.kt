package com.backend.domain.usecases.guitar

import com.backend.data.instruments.GuitarDTO
import com.backend.data.instruments.GuitarDataSource
import com.backend.utils.ServerResponse
import io.ktor.http.*

class InsertNewGuitar(private val guitarDataSource: GuitarDataSource){

    suspend operator fun invoke(guitar: GuitarDTO): Any {
        return when(ValidateGuitar().invoke(guitar)){
            is ServerResponse.Success -> {
                when (guitarDataSource.insertNewGuitar(guitar)) {
                    is ServerResponse.Success -> HttpStatusCode.OK
                    is ServerResponse.Failure -> HttpStatusCode.BadRequest
                }
            }
            is ServerResponse.Failure -> HttpStatusCode.BadRequest
        }
    }
}
