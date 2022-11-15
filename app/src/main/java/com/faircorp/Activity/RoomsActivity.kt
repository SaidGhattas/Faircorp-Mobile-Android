package com.faircorp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.R
import com.faircorp.api.ApiServices
import com.faircorp.model.RoomAdapter
import com.faircorp.model.RoomListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomsActivity : BasicActivity(), RoomListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)
        val buidling_id = intent.getLongExtra(MainActivity.BUILDING_ID, -1)
        val recyclerView = findViewById<RecyclerView>(R.id.list_rooms) // (2)
        val adapter = RoomAdapter(this) // (3)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        val createBtn: Button = findViewById(R.id.btn_room_create)
        createBtn.setOnClickListener {
            val intent = Intent(this, RoomActivity::class.java).putExtra(MainActivity.BUILDING_ID, buidling_id)
            startActivity(intent)
        }

            lifecycleScope.launch(context = Dispatchers.IO) { // (1)
                runCatching {
                    ApiServices.RoomsApiService.findByBuildingId(buidling_id).execute()
                } // (2)
                    .onSuccess {
                        withContext(context = Dispatchers.Main) { // (3)
                            adapter.update(it.body() ?: emptyList())
                        }
                    }
                    .onFailure {
                        withContext(context = Dispatchers.Main) { // (3)
                            Toast.makeText(
                                applicationContext,
                                "Error on Rooms loading $it",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }


    override fun onWindowClicked(id: Long) {
        val intent = Intent(this, WindowsActivity::class.java).apply {
            putExtra(MainActivity.ROOM_ID, id)
        }
        startActivity(intent)    }

    override fun onHeaterClicked(id: Long) {
        val intent = Intent(this, HeatersActivity::class.java).apply {
            putExtra(MainActivity.ROOM_ID, id)
        }
        startActivity(intent)
    }

    override fun onRoomSelected(id: Long) {
        val buidling_id = intent.getLongExtra(MainActivity.BUILDING_ID, -1)

        val intent = Intent(this, RoomActivity::class.java).putExtra(MainActivity.ROOM_ID, id) .putExtra(MainActivity.BUILDING_ID, buidling_id)
        startActivity(intent)
    }

}
