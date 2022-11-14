package com.faircorp.dto

enum class WindowStatus { OPEN, CLOSED}

data class WindowDto(
    val id: Long,
    val name: String,
    val windowStatus: WindowStatus,
    val roomId: Long
    )