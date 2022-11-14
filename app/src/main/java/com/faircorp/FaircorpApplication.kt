package com.faircorp

import android.app.Application
import androidx.room.Room
import com.faircorp.dao.FaircorpDatabase

class FaircorpApplication : Application() {

    val database: FaircorpDatabase by lazy {
        Room.databaseBuilder(this, FaircorpDatabase::class.java, "faircorpdb")
            .build()
    }
    
}
