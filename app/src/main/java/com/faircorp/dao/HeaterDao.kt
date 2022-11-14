package com.faircorp.dao

import androidx.room.*
import com.faircorp.model.Heater


@Dao
interface HeaterDao {
    @Query("select * from rheater order by name")
    fun findAll(): List<Heater>

    @Query("select * from rheater where id = :heaterId")
    fun findById(heaterId: Int): Heater

    @Insert
    suspend fun create(heater: Heater)

    @Update
    suspend fun update(heater: Heater): Int

    @Delete
    suspend fun delete(heater: Heater)

    @Query("delete from rheater")
    suspend fun clearAll()
    //find by room id list
    @Query("select * from rheater where room_id = :roomId")
    fun findByRoomId(roomId: Long): List<Heater>

}