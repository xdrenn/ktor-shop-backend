package com.backend.plugins

import com.backend.controller.AccessoriesController
import com.backend.controller.GuitarsController
import com.backend.controller.UserController

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureRouting(
    userController: UserController,
    guitarsController: GuitarsController,
    accessoriesController: AccessoriesController
) {
    routing {
        route("api") {
            post("signup") { userController.register(this.context) }
            post("login") { userController.login(this.context) }
            post("user") { userController.getUser(this.context)}
        }
        authenticate {
            route("api") {
                post("delete") { userController.deleteUser(this.context) }
                post("guitar/category") {
                    guitarsController.getGuitarByCategory(this.context)
                }
                post("guitar/brand") {
                    guitarsController.getGuitarByBrand(this.context)
                }
                post("guitar/post") { guitarsController.postGuitar(this.context) }

                post("guitar/id") { guitarsController.getGuitarById(this.context) }

                post("accessory/category") {
                    accessoriesController.getAccessoryByCategory(this.context)
                }
                post("accessory/subcategory") {
                    accessoriesController.getAccessoryBySubcategory(this.context)
                }
                post("accessory/id"){
                    accessoriesController.getAccessoryById(this.context)
                }
                post("accessory/post") {
                    accessoriesController.postAccessory(this.context)
                }
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
