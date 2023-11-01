package com.backend.data.accessories

import com.backend.utils.ServerResponse

interface AccessoriesDataSource {
   suspend fun getAccessoryByCategory(category: String): ServerResponse<List<AccessoriesDTO>>

   suspend fun getAccessoryBySubcategory(subcategory: String): ServerResponse<List<AccessoriesDTO>>
   suspend fun getAccessoryById(id: Int): ServerResponse<List<AccessoriesDTO>>
   suspend fun insertNewAccessory(accessoriesDTO: AccessoriesDTO): ServerResponse<Boolean>
}