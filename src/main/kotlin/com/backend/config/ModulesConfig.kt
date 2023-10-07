package com.backend.config

import com.backend.controller.AccessoriesController
import com.backend.controller.GuitarsController
import com.backend.controller.UserController
import com.backend.data.accessories.AccessoriesDataSourceImpl
import com.backend.data.instruments.GuitarDataSourceImpl
import com.backend.data.users.UserDataSourceImpl
import com.backend.hash.HashingServiceImpl
import com.backend.security.JWTokenService
import com.backend.security.TokenConfig
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

object ModulesConfig {
    private val userModule = Kodein.Module("USER") {
        val userDataSourceImpl = UserDataSourceImpl()
        val tokenService = JWTokenService()
        val tokenConfig = TokenConfig()
        val hashingService = HashingServiceImpl()
        bind() from singleton { UserController(userDataSourceImpl, hashingService, tokenService, tokenConfig) }
    }

    private val guitarModule = Kodein.Module("GUITAR"){
        val guitarDataSource = GuitarDataSourceImpl()
        bind() from singleton { GuitarsController(guitarDataSource) }
    }
    private val accessoriesModule = Kodein.Module("ACCESSORIES"){
        val accessoriesDataSource = AccessoriesDataSourceImpl()
        bind() from singleton { AccessoriesController(accessoriesDataSource) }
    }
    internal val kodein = Kodein {
        import(userModule)
        import(guitarModule)
        import(accessoriesModule)
    }
}