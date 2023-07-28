package com.backend

import com.backend.data.user.UserDataSourceImpl
import com.backend.db.DatabaseFactory
import com.backend.hash.HashingServiceImpl
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.backend.plugins.*
import com.backend.security.JWTokenService
import com.backend.security.TokenConfig
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*

fun main() {
    DatabaseFactory.init()
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    val userDataSourceImpl = UserDataSourceImpl()
    val tokenService = JWTokenService()
    val tokenConfig = TokenConfig(
        issuer =  System.getenv("ISSUER"),
        audience = System.getenv("ISSUER"),
        expires = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = HashingServiceImpl()

    configureSecurity(tokenConfig)
    configureSerialization()
    configureRouting(userDataSourceImpl, hashingService, tokenService, tokenConfig)


}
