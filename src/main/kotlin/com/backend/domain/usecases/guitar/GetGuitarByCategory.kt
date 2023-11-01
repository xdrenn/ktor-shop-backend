package com.backend.domain.usecases.guitar

import com.backend.data.instruments.GuitarDataSource
import com.backend.data.remote.GuitarResponse
import com.backend.utils.ServerResponse

class GetGuitarByCategory(private val guitarDataSource: GuitarDataSource) {
    suspend operator fun invoke(category: String): GuitarResponse {
        return when (val guitar = guitarDataSource.getGuitarByCategory(category)) {
            is ServerResponse.Success -> GuitarResponse(guitar.data)
            is ServerResponse.Failure -> GuitarResponse(null)
        }
    }
}