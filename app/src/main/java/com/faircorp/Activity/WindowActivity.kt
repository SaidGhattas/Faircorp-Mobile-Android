package com.faircorp.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.faircorp.R
import com.faircorp.dto.WindowDto
import com.faircorp.dto.WindowStatus
import com.faircorp.viewmodel.WindowViewModel

class WindowActivity : BasicActivity()  {

    private val viewModel: WindowViewModel by viewModels {
        WindowViewModel.factory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        val id = intent.getLongExtra(MainActivity.WINDOW_ID, 0)
        val roomId = intent.getLongExtra(MainActivity.ROOM_ID, 0)

        val name = findViewById<EditText>(R.id.edit_window_name)
        val savebtn = findViewById<Button> (R.id.btn_window_Edit)
        val status = WindowStatus.CLOSED        //status always closed when creating a new window


        savebtn.setOnClickListener {
            val name = name.text.toString()
            (if (id == 0L) null else id)?.let { it1 -> WindowDto( id= it1, name, status,roomId) }
                ?.let { it2 -> viewModel.updateWindow(it2 )}
        }
            finish()
        }
    }


