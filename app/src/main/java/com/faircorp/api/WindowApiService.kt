package com.faircorp.api

import com.faircorp.dto.WindowDto
import retrofit2.Call
import retrofit2.http.*

interface WindowApiService {
    @GET("windows")
    fun findAll(): Call<List<WindowDto>>

    @GET("windows/{id}")
    fun findById(@Path("id") id: Long): Call<WindowDto>

    @PUT("windows/{id}/switch")
    fun switchWindow(@Path("id") id: Long): Call<Void>

    @PUT("windows/{id}")
    fun updateWindow(@Body window: WindowDto): Call<WindowDto>

    @GET("windows/room/{roomId}")
    fun findByRoomId(@Path("roomId") roomId: Long): Call<List<WindowDto>>


}