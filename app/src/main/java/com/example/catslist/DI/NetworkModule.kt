package com.example.catslist.DI

import android.provider.SyncStateContract
import com.example.catslist.network.FakeStoreApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val BASE_URL = "https://fakestoreapi.com/"

        @Provides
        @Singleton
        fun provideGson(): Gson =  GsonBuilder().setLenient().create()

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

        @Provides
        @Singleton
        fun provideLoginInterceptor() : HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }

        @Provides
        @Singleton
        fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) = OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        @Provides
        @Singleton
        fun provideFakeStoreApi(retrofit: Retrofit): FakeStoreApi = retrofit.create(FakeStoreApi::class.java)
}