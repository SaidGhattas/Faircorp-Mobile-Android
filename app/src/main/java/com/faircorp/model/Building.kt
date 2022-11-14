package com.faircorp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.faircorp.dto.BuildingDto

@Entity(tableName = "rbuilding")
data class Building(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val Name: String,
    @ColumnInfo(name = "building_address") val Address: String,
    @ColumnInfo(name = "building_city") val City: String,
    @ColumnInfo(name = "building_zipCode") val ZipCode: Int

) {
    fun toDto(): BuildingDto =
        BuildingDto(id.toLong(), Name, Address, City, ZipCode)
}