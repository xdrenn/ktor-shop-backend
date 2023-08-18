package com.backend.config

import com.backend.controller.ClothesController
import com.backend.controller.UserController
import com.backend.data.clothes.ClothesDataSourceImpl
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

    private val clothesModule = Kodein.Module("CLOTHES"){
        val clothesDataSource = ClothesDataSourceImpl()
        bind() from singleton { ClothesController(clothesDataSource) }
    }
    internal val kodein = Kodein {
        import(userModule)
        import(clothesModule)
    }
}