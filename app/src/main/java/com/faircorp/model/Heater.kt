package com.faircorp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.faircorp.dto.*


@Entity(tableName = "rheater")
data class Heater(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo(name = "room_id") val roomId: Int,
    @ColumnInfo(name = "power") val power: Int,
    @ColumnInfo(name = "heater_status") val heaterStatus: HeaterStatus
) {
    fun toDto(): HeaterDto =
        HeaterDto(id.toLong(), name, power, heaterStatus, roomId.toLong())
}