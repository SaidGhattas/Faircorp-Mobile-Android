package com.faircorp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.faircorp.dto.RoomDto


@Entity(tableName = "rroom")
data class Room (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo(name = "building_id") val buildingId: Long,
    @ColumnInfo(name = "current_temperature") val currentTemperature: Double?,
    @ColumnInfo(name = "target_temperature") val targetTemperature: Double?
) {
    fun toDto(): RoomDto =
        RoomDto(id.toLong(), name,  buildingId, currentTemperature, targetTemperature)
}