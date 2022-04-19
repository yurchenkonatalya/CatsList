package com.example.catslist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RoomOwnerDao {
    @Query("SELECT * FROM RoomOwner ")
    fun getOwners(): List<RoomOwner>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOwner(roomOwner: RoomOwner)
}