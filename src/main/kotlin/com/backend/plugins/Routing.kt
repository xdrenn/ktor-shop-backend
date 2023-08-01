package com.backend.plugins

import com.backend.controller.UserController

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureRouting(
    userController: UserController
) {
    routing {
        route("users") {
            post("register") { userController.register(this.context) }
            post("login") { userController.login(this.context) }
        }

        authenticate {
            get("authenticate") {
                call.respond(HttpStatusCode.OK)
            }
        }

        authenticate {
            get("secret") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.getClaim("userId", String::class)
                call.respond(HttpStatusCode.OK, "Your userId is $userId")
            }
        }
    }
}
