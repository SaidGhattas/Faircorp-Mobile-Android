package com.faircorp.dao

import androidx.room.*
import com.faircorp.model.Room

@Dao
interface RoomDao {
    @Query("select * from rroom order by name")
    fun findAll(): List<Room>

    @Query("select * from rroom where id = :roomId")
    fun findById(roomId: Int): Room

    @Insert
    suspend fun create(room: Room)

    @Update
    suspend fun update(room: Room): Int

    @Delete
    suspend fun delete(room: Room)

    @Query("delete from rroom")
    suspend fun clearAll()
    //find by building id list
    @Query("select * from rroom where building_Id = :buildingId")
    fun findByBuildingId(buildingId: Long): List<Room>


}