package com.backend.plugins

import com.backend.controller.UserController
import com.backend.routes.*
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting(
    userController: UserController
) {
    routing {
        users(userController)
        authenticate()
        getSecretInfo()
    }
}
