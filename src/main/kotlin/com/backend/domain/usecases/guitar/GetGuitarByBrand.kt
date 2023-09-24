package com.backend.domain.usecases.guitar

import com.backend.data.instruments.GuitarDataSource
import com.backend.data.remote.GuitarResponse
import com.backend.utils.ServerResponse

class GetGuitarByBrand(private val guitarDataSource: GuitarDataSource) {
    suspend operator fun invoke(brand: String): GuitarResponse {
        return when (val guitar = guitarDataSource.getGuitarByBrand(brand)) {
            is ServerResponse.Success -> GuitarResponse(guitar.data)
            is ServerResponse.Failure -> GuitarResponse(null)
        }
    }
}