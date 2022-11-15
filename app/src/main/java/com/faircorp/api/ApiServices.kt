package com.faircorp.api

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiServices {
    const val API_USERNAME = "user"
    const val API_PASSWORD = "password"
    const val API_URL = "http://saidghattas.cleverapps.io/api/"
    val windowsApiService : WindowApiService by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(API_USERNAME, API_PASSWORD))
            .build()

        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .baseUrl(API_URL)
            .build()
            .create(WindowApiService::class.java)
    }
    val RoomsApiService : RoomApiService by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(API_USERNAME, API_PASSWORD))
            .build()

        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .baseUrl(API_URL)
            .build()
            .create(RoomApiService::class.java)
    }
    val BuildingsApiService : BuildingApiService by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(API_USERNAME, API_PASSWORD))
            .build()

        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .baseUrl(API_URL)
            .build()
            .create(BuildingApiService::class.java)
    }
    val heatersApiService : HeaterApiService by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(API_USERNAME, API_PASSWORD))
            .build()

        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .baseUrl(API_URL)
            .build()
            .create(HeaterApiService::class.java)
    }

}
class BasicAuthInterceptor(val username: String, val password: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain
            .request()
            .newBuilder()
            .header("Authorization", Credentials.basic(username, password))
            .build()
        return chain.proceed(request)
    }

}