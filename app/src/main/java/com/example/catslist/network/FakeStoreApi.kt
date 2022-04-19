package com.example.catslist.network

import android.provider.SyncStateContract
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

interface FakeStoreApi {
    @GET("products/category/{category}")
    suspend fun getProductsFromCategory(
        @Path("category") category: String
    ): Response<List<ResponseProduct>>
    }