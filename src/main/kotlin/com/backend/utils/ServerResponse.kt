package com.backend.utils

import io.ktor.http.*

sealed class ServerResponse <out T> {
    data class Success<out T>(
        val data: T? = null
    ) : ServerResponse<T>()

    data class Failure(
        val message: String? = null,
        val statusCode: HttpStatusCode? = null
    ) : ServerResponse<Nothing>()
}