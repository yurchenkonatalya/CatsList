package com.example.catslist.model

import com.example.catslist.db.RoomItem
import com.example.catslist.db.RoomItemDao
import com.example.catslist.network.FakeStoreApi
import com.example.catslist.network.ResponseProduct
import retrofit2.Response

data class HomeRepositoryDataResponse(
    var fromNetwork: Boolean = false,
    var data: List<RoomItem>? = null
)

const val CATEGORY_1 = "electronics"
const val CATEGORY_2 = "jewelery"

class HomeRepository(
    private val fakeStoreApi: FakeStoreApi,
    private val dao: RoomItemDao
) {

    suspend fun loadData(): HomeRepositoryDataResponse {
        val response = HomeRepositoryDataResponse()

        response.data = dao.getItems()

        val category1Data: Response<List<ResponseProduct>>
        val category2Data: Response<List<ResponseProduct>>

        try {
            category1Data = fakeStoreApi.getProductsFromCategory(CATEGORY_1)
            category2Data = fakeStoreApi.getProductsFromCategory(CATEGORY_2)
        } catch (e: Exception) {
            return response
        }

        if (!category1Data.isSuccessful || !category2Data.isSuccessful)
            return response

        val itemsCategory1 = category1Data.body() ?: return response
        val itemsCategory2 = category2Data.body() ?: return response

        dao.deleteAllItems()

        for (item in itemsCategory1) {
            dao.insertItem(item.toRoomItem())
        }

        for (item in itemsCategory2) {
            dao.insertItem(item.toRoomItem())
        }

        return response.apply {
            data = itemsCategory1.map { it.toRoomItem() } + itemsCategory2.map { it.toRoomItem() }
            fromNetwork = true
        }
    }
}