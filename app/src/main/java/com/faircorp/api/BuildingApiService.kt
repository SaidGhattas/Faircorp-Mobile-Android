package com.faircorp.api

import com.faircorp.dto.BuildingDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface BuildingApiService {
    @GET("buildings")
    fun findAll(): Call<List<BuildingDto>>

    @GET("buildings/{id}")
    fun findById(@Path("id") id: Long): Call<BuildingDto>

    @PUT("buildings/{id}")
    fun updateBuilding(@Path("id") id: Long, @Body building: BuildingDto): Call<BuildingDto>

}