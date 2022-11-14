package com.faircorp.dao

import androidx.room.*
import com.faircorp.model.Building

@Dao
interface BuildingDao {
    @Query("select * from rbuilding order by name")
    fun findAll(): List<Building>

    @Query("select * from rbuilding where id = :buildingId")
    fun findById(buildingId: Int): Building

    @Insert
    suspend fun create(building: Building)

    @Update
    suspend fun update(building: Building): Int

    @Delete
    suspend fun delete(building: Building)

    @Query("delete from rbuilding")
    suspend fun clearAll()
}