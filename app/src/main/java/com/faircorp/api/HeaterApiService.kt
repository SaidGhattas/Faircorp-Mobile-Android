package com.faircorp.api

import com.faircorp.dto.HeaterDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface HeaterApiService {
    @GET("heaters")
    fun findAll(): Call<List<HeaterDto>>

    @GET("heaters/{id}")
    fun findById(@Path("id") id: Long): Call<HeaterDto>

    @PUT("heaters/{id}")
    fun updateHeater(@Body heater: HeaterDto): Call<HeaterDto>

    @PUT("heaters/{id}/switch")
    fun switchHeater(@Path("id") id: Long): Call<Void>

    @GET("heaters/%7Bid%7D/room?roomId={roomId}")
    fun findByRoomId(@Path("roomId") id: Long): Call<List<HeaterDto>>


}