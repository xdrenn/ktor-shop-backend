package com.backend.domain.usecases.accessory

import com.backend.data.accessories.AccessoriesDataSource
import com.backend.data.remote.AccessoryResponse
import com.backend.utils.ServerResponse

class GetAccessoryBySubcategory(private val accessoriesDataSource: AccessoriesDataSource) {
    suspend operator fun invoke(subcategory: String): AccessoryResponse {
        return when (val accessory = accessoriesDataSource.getAccessoryBySubcategory(subcategory)) {
            is ServerResponse.Success -> AccessoryResponse(accessory.data)
            is ServerResponse.Failure -> AccessoryResponse(null)
        }
    }
}