package com.example.catslist.db

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room

@Database(entities = arrayOf(RoomItem::class,RoomOwner::class), version = 1)
abstract class RoomSingleton : RoomDatabase() {
    abstract fun roomItemDao():RoomItemDao
    abstract fun roomOwnerDao():RoomOwnerDao
}