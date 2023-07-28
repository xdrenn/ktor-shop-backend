package com.backend.routes

import com.backend.data.remote.LoginRequest
import com.backend.data.user.UserDTO
import com.backend.data.user.UserDataSource
import com.backend.domain.usecases.InsertNewUser
import com.backend.hash.HashingService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signUp(
    hashingService: HashingService,
    userDataSource: UserDataSource
) {
    post("/register") {
        val request = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val areFieldsEmpty = request.login.isBlank() || request.password.isBlank()
        if (areFieldsEmpty) {
            call.respond(HttpStatusCode.BadRequest, "Fields must not be empty")
            return@post
        }

        val saltedHash = hashingService.generateSaltedHash(request.password)
        InsertNewUser(userDataSource).invoke(
            UserDTO(
                login = request.login,
                password = saltedHash.hash,
                salt = saltedHash.salt
            )
        )

    }
}
