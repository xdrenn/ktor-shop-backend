package com.backend.plugins

import com.backend.data.user.UserDataSource
import com.backend.hash.HashingService
import com.backend.routes.authenticate
import com.backend.routes.getSecretInfo
import com.backend.routes.login
import com.backend.routes.signUp
import com.backend.security.TokenConfig
import com.backend.security.TokenService
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    routing {
        login(userDataSource, hashingService, tokenService, tokenConfig)
        signUp(hashingService, userDataSource)
        authenticate()
        getSecretInfo()
    }
}
