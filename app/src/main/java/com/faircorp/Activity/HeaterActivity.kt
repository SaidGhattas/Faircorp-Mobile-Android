package com.faircorp.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.faircorp.R
import com.faircorp.dto.HeaterDto
import com.faircorp.dto.HeaterStatus
import com.faircorp.viewmodel.HeaterViewModel

class HeaterActivity : BasicActivity() {
    private val viewModel: HeaterViewModel by viewModels {
        HeaterViewModel.factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heater)
        val id = intent.getLongExtra(MainActivity.HEATER_ID,0)
        val roomId = intent.getLongExtra(MainActivity.ROOM_ID,0)
        val editName = findViewById<EditText>(R.id.edit_heater_name)
        val editPower = findViewById<EditText>(R.id.edit_heater_power)
        val heaterStatus = HeaterStatus.OFF // Creating a new heater is always initiated OFF
        val savebtn = findViewById<Button>(R.id.btn_heater_edit)


        savebtn.setOnClickListener {
            val name = editName.text.toString()
            val power = editPower.text.toString().toInt()
            (if (id==0L) null else id)?.let { it1 -> HeaterDto(id = it1,name,power,heaterStatus,roomId) }
                ?.let { it2 ->
                    viewModel.updateHeater(it2)
                        .observe(this) {
                            finish()
                        }
                }
                }
    }

}