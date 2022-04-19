package com.example.catslist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RoomItemDao {
    @Query("SELECT * FROM RoomItem")
    fun getItems(): List<RoomItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(roomItem: RoomItem)

    @Query("DELETE FROM RoomItem")
    fun deleteAllItems()
}