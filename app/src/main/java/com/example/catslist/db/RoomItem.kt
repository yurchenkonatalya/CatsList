package com.example.catslist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomItem (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id:Long,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "price")
    var price: Float,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "image")
    var image: String
        )