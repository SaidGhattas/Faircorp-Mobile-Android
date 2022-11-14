package com.faircorp.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.faircorp.model.Building
import com.faircorp.model.EnumConverters
import com.faircorp.model.Heater
import com.faircorp.model.Window
import com.faircorp.model.Room



@Database(entities = [Window::class,Heater::class, Building::class, Room::class], version = 1)
@TypeConverters(EnumConverters::class)
abstract class FaircorpDatabase : RoomDatabase() {
    abstract fun windowDao(): WindowDao
    abstract fun heaterDao(): HeaterDao
    abstract fun buildingDao(): BuildingDao
    abstract fun roomDao(): RoomDao
}


