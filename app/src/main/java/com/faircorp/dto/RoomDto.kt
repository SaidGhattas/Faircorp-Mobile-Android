package com.faircorp.dto

data class RoomDto (
    val id: Long,
    val name: String,
    val buildingId: Long,
    val current_temperature: Double?,
    val target_temperature: Double?
    )