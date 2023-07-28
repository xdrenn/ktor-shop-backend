package com.backend.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.backend.security.TokenConfig
import io.ktor.server.application.*

fun Application.configureSecurity(config: TokenConfig) {
    authentication {
        jwt {
            realm = System.getenv("REALM")
            verifier(
                JWT
                    .require(Algorithm.HMAC256("secret"))
                    .withAudience("users")
                    .withIssuer("http://0.0.0.0:8080")
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains("users")) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }
}
