package com.faircorp.dto

data class BuildingDto (
    val id: Long,
    val name: String,
    val address: String,
    val city: String,
    val zipCode: Int
)