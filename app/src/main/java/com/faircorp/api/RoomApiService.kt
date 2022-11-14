package com.faircorp.api

import com.faircorp.dto.RoomDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface RoomApiService {
    @GET("rooms")
    fun findAll(): Call<List<RoomDto>>

    @GET("rooms/{id}")
    fun findById(@Path("id") id: Long): Call<List<RoomDto>>

    @GET("rooms/{id}/building/{buildingId}")
    fun findByBuildingId(@Path("buildingId") id: Long): Call<List<RoomDto>>
    @PUT("rooms/{id}")
    fun updateRoom( @Body room: RoomDto): Call<RoomDto>
}