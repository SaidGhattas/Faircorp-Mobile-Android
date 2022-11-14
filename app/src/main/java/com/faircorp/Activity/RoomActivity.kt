package com.faircorp.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.faircorp.R
import com.faircorp.dto.RoomDto
import com.faircorp.viewmodel.RoomViewModel

class RoomActivity : BasicActivity() {
    private val viewModel: RoomViewModel by viewModels {
        RoomViewModel.factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val id = intent.getLongExtra(MainActivity.ROOM_ID, 0)
        val name = findViewById<EditText>(R.id.edit_room_name)
//        val buildingId = intent.getLongExtra(MainActivity.BUILDING_ID, 0)
        val newBuildingId = findViewById<EditText>(R.id.edit_room_building)
        val currentTemperature = findViewById<EditText>(R.id.edit_room_current_temp)
        val targetTemperature = findViewById<EditText>(R.id.edit_room_target_temp)
        val savebtn = findViewById<Button>(R.id.btn_room_edit)


        savebtn.setOnClickListener {
            val name = name.text.toString()
            val newBuildingId = newBuildingId.text.toString().toLong()
            val currentTemperature = currentTemperature.text.toString().toDouble()
            val targetTemperature = targetTemperature.text.toString().toDouble()
            (if (id == 0L) null else id)?.let { it1 ->
                RoomDto(
                    id = it1,
                    name,
                    newBuildingId,
                    currentTemperature,
                    targetTemperature
                )
            }
                ?.let { it2 -> viewModel.updateRoom(it2) }
        }
        finish()
    }
}