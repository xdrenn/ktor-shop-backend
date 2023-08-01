package com.backend


import com.backend.config.ModulesConfig
import com.backend.controller.UserController
import com.backend.db.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.backend.plugins.*
import com.backend.security.TokenConfig
import org.kodein.di.generic.instance

fun main() {
    DatabaseFactory.init()
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val userController by ModulesConfig.kodein.instance<UserController>()
    val tokenConfig = TokenConfig()

    configureSecurity(tokenConfig)
    configureSerialization()
    configureRouting(userController)


}
