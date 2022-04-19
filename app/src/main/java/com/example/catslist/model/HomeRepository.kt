package com.example.catslist.model

import com.example.catslist.db.RoomItem
import com.example.catslist.db.RoomItemDao
import com.example.catslist.network.FakeStoreApi

class HomeRepository(
    private val fakeStoreApi: FakeStoreApi,
    private val dao: RoomItemDao
) {

    suspend fun loadData(): List<RoomItem>?{
        val category1Data = fakeStoreApi.getProductsFromCategory("electronics")
        val category2Data = fakeStoreApi.getProductsFromCategory("jewelery")

        if(!category1Data.isSuccessful || !category2Data.isSuccessful)
            return null

        val itemsCategory1 = category1Data.body() ?: return null
        val itemsCategory2 = category2Data.body() ?: return null

        dao.deleteAllItems()

        for(item in itemsCategory1){
            dao.insertItem(item.toRoomItem())
        }

        for(item in itemsCategory2){
            dao.insertItem(item.toRoomItem())
        }

        return itemsCategory1.map { it.toRoomItem() }+itemsCategory2.map { it.toRoomItem() }
    }
}