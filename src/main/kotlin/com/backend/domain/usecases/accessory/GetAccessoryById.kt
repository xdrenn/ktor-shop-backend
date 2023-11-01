package com.backend.domain.usecases.accessory

import com.backend.data.accessories.AccessoriesDataSource
import com.backend.data.remote.AccessoryResponse
import com.backend.utils.ServerResponse

class GetAccessoryById(private val accessoriesDataSource: AccessoriesDataSource) {
    suspend operator fun invoke(id: Int): AccessoryResponse {
        return when (val accessory = accessoriesDataSource.getAccessoryById(id)) {
            is ServerResponse.Success -> AccessoryResponse(accessory.data)
            is ServerResponse.Failure -> AccessoryResponse(null)
        }
    }
}