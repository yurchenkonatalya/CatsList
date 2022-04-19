package com.example.catslist.DI

import com.example.catslist.db.RoomItemDao
import com.example.catslist.model.HomeRepository
import com.example.catslist.network.FakeStoreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
    @InstallIn(ActivityRetainedComponent::class)
    object RepositoryModule {
        @Provides
        @ActivityRetainedScoped
        fun provideAdListRepository(fakeStoreApi: FakeStoreApi, roomItemDao: RoomItemDao) =
            HomeRepository(fakeStoreApi, roomItemDao)
}