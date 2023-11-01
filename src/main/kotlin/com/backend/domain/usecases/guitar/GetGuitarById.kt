package com.backend.domain.usecases.guitar

import com.backend.data.instruments.GuitarDataSource
import com.backend.data.remote.GuitarResponse
import com.backend.utils.ServerResponse

class GetGuitarById(private val guitarDataSource: GuitarDataSource) {
    suspend operator fun invoke(id: Int): GuitarResponse {
        return when (val guitar = guitarDataSource.getGuitarById(id)) {
            is ServerResponse.Success -> GuitarResponse(guitar.data)
            is ServerResponse.Failure -> GuitarResponse(null)
        }
    }
}