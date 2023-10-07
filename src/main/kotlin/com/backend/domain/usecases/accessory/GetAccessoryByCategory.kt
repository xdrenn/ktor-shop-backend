package com.backend.domain.usecases.accessory

import com.backend.data.accessories.AccessoriesDataSource
import com.backend.data.remote.AccessoryResponse
import com.backend.utils.ServerResponse

class GetAccessoryByCategory(private val accessoriesDataSource: AccessoriesDataSource) {
    suspend operator fun invoke(category: String): AccessoryResponse {
        return when (val accessory = accessoriesDataSource.getAccessoryByCategory(category)) {
            is ServerResponse.Success -> AccessoryResponse(accessory.data)
            is ServerResponse.Failure -> AccessoryResponse(null)
        }
    }
}