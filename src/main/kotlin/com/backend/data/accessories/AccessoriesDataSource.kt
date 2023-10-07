package com.backend.data.accessories

import com.backend.utils.ServerResponse

interface AccessoriesDataSource {

   suspend fun getAccessoryByCategory(category: String): ServerResponse<AccessoriesDTO>
   suspend fun insertNewAccessory(accessoriesDTO: AccessoriesDTO): ServerResponse<Boolean>
}