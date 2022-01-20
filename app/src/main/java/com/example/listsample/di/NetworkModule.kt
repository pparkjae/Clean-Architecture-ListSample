package com.example.listsample.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.listsample.data.remote.api.BookApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun providesHeaderInterceptor() = Interceptor {
        it.proceed(
            it.request().newBuilder()
                .addHeader("Authorization", "KakaoAK ae2a368f1a650a783a2fce42e643b7ff")
                .build()
        )
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(interceptor: Interceptor) = OkHttpClient.Builder().apply {
        hostnameVerifier { _, _ -> true }
        addInterceptor(interceptor)
        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        connectTimeout(15, TimeUnit.SECONDS)
        writeTimeout(15, TimeUnit.SECONDS)
        readTimeout(15, TimeUnit.SECONDS)
    }.build()

    @Provides
    @Singleton
    fun provideConverterFactory(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = true
            isLenient = true
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit =
        Retrofit.Builder().apply {
            addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            baseUrl("https://dapi.kakao.com")
            client(okHttpClient)
        }.build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): BookApi =
        retrofit.create(BookApi::class.java)
}