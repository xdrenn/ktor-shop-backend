package com.backend.plugins

import com.backend.controller.ClothesController
import com.backend.controller.UserController

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureRouting(
    userController: UserController,
    clothesController: ClothesController
) {
    routing {
        route("api") {
            post("signup") { userController.register(this.context) }
            post("login") { userController.login(this.context) }
        }
        authenticate {
            route("api") {
                get("clothes") { clothesController.getClothes(this.context) }
                post("clothes/post") { clothesController.postClothes(this.context) }
            }
        }

        authenticate {
            get("authenticate") {
                val principal = call.principal<JWTPrincipal>()
                principal?.getClaim("userId", String::class)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
