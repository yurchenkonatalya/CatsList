package com.example.catslist.DI

import android.content.Context
import androidx.room.Room
import com.example.catslist.db.RoomSingleton
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

    @Module
    @InstallIn(SingletonComponent::class)
    object DatabaseModule {

        @Provides
        @Singleton
        fun provideRoomDB(@ApplicationContext applicationContext: Context) : RoomSingleton =
            Room.databaseBuilder(
                applicationContext,
                RoomSingleton::class.java,
                "RoomDB"
            ).build()

        @Provides
        @Singleton
        fun provideRoomItemDao(database : RoomSingleton) = database.roomItemDao()
}