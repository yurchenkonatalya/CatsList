package com.example.catslist.network

import com.example.catslist.db.RoomItem

data class ResponseProduct (
    var id: Long,
    var title: String,
    var price: Float,
    var category: String,
    var description: String,
    var image: String
        ){
    fun toRoomItem(): RoomItem {
        return RoomItem(
            id,
            title,
            price,
            category,
            description,
            image
        )
    }
}