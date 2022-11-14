package com.faircorp.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.R
import com.faircorp.model.HeaterAdapter
import com.faircorp.model.OnHeaterSelectedListener
import com.faircorp.viewmodel.HeaterViewModel
import com.faircorp.viewmodel.State

class HeatersActivity : BasicActivity(), OnHeaterSelectedListener
{
    private val viewModel: HeaterViewModel by viewModels {
        HeaterViewModel.factory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heaters)

        val recyclerView = findViewById<RecyclerView>(R.id.list_heaters) // (2)
        val adapter = HeaterAdapter(this) // (3)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        val roomID = intent.getLongExtra(MainActivity.ROOM_ID, 0)
        Log.i("HeaterActivity","Room ID: $roomID")

        viewModel.findByRoomId(roomID).observe(this) { heaters ->

            adapter.update(heaters)
            viewModel.networkState.observe(this) { state ->
                if(state == State.OFFLINE) {
                    Toast.makeText(this,"Offline mode, the last known values are displayed", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
    override fun HeaterSwitchListener(id: Long) {
        viewModel.switchHeater(id)
    }
    override fun onHeaterSelected(id: Long) {
        val intent = Intent(this, HeaterActivity::class.java).putExtra(MainActivity.HEATER_NAME_PARAM, id)
        startActivity(intent)
    }


}
