package com.backend.controller

import com.backend.data.remote.LoginRequest
import com.backend.data.remote.LoginResponse
import com.backend.data.users.UserDTO
import com.backend.data.users.UserDataSource
import com.backend.domain.usecases.user.GetUserByUsername
import com.backend.domain.usecases.user.InsertNewUser
import com.backend.domain.usecases.user.NameInUseCheck
import com.backend.domain.usecases.user.ValidatePassword
import com.backend.hash.HashingService
import com.backend.security.TokenClaim
import com.backend.security.TokenConfig
import com.backend.security.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class UserController(
    private val userDataSource: UserDataSource,
    private val hashingService: HashingService,
    private val tokenService: TokenService,
    private val tokenConfig: TokenConfig
) {

    suspend fun login(call: ApplicationCall) {
        val request = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return
        }

        val user = GetUserByUsername(userDataSource).invoke(request.username).user
        if (user == null) {
            call.respond(HttpStatusCode.NotFound, "Please, sign up")
            return
        }
        val isValidPassword = ValidatePassword(hashingService = hashingService, request).invoke(user)
        if(!isValidPassword){
            call.respond(HttpStatusCode.Conflict, "Incorrect password")
            return
        }

        val token = tokenService.generate(
            config = tokenConfig,
            TokenClaim(
                name = "userId",
                value = user.id.toString()
            )
        )
        call.respond(
            status = HttpStatusCode.OK,
            message = LoginResponse(
                token = token
            )
        )
    }

    suspend fun register(call: ApplicationCall) {
        val request = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return
        }

        val areFieldsEmpty = request.username.isBlank() || request.password.isBlank()
        if (areFieldsEmpty) {
            call.respond(HttpStatusCode.BadRequest, "Fields must not be empty")
            return
        }


        if (NameInUseCheck(userDataSource).invoke(request.username)) {
            call.respond(HttpStatusCode.BadRequest, "Name is already in use")
        } else {
            val saltedHash = hashingService.generateSaltedHash(request.password)
            InsertNewUser(userDataSource).invoke(
                UserDTO(
                    username = request.username,
                    password = saltedHash.hash,
                    salt = saltedHash.salt
                )
            )
            call.respond(HttpStatusCode.OK, "You've been successfully registered!")
        }
    }
}
