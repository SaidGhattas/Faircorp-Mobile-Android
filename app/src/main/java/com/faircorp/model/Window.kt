package com.faircorp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.faircorp.dto.WindowDto
import com.faircorp.dto.WindowStatus

@Entity(tableName = "rwindow")
data class Window(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo(name = "room_id") val roomId: Long,
    @ColumnInfo(name = "window_status") val windowStatus: WindowStatus
) {
    fun toDto(): WindowDto =
        WindowDto(id.toLong(), name, roomId = roomId, windowStatus = windowStatus)
}