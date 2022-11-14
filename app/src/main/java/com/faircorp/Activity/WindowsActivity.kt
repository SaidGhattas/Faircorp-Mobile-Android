package com.faircorp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.Activity.MainActivity.Companion.WINDOW_NAME_PARAM
import com.faircorp.R
import com.faircorp.api.ApiServices
import com.faircorp.model.OnWindowSelectedListener
import com.faircorp.model.WindowAdapter
import com.faircorp.viewmodel.State
import com.faircorp.viewmodel.WindowViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WindowsActivity : BasicActivity(), OnWindowSelectedListener {
    private val viewModel: WindowViewModel by viewModels {
        WindowViewModel.factory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_windows)

        val recyclerView = findViewById<RecyclerView>(R.id.list_windows) // (2)
        val adapter = WindowAdapter(this) // (3)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        viewModel.findByRoomId(intent.getLongExtra(MainActivity.ROOM_ID,0)).observe(this) { windows ->
            adapter.update(windows)
            viewModel.networkState.observe(this) { state ->
                if(state == State.OFFLINE) {
                    Toast.makeText(this,"Offline mode, the last known values are displayed", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
    override fun onWindowSelected(id: Long) {
        val intent = Intent(this, WindowActivity::class.java).putExtra(WINDOW_NAME_PARAM, id)
        startActivity(intent)
    }


    override fun WindowSwitchListener(id: Long) {
        lifecycleScope.launch(Dispatchers.IO) {
            runCatching { ApiServices.windowsApiService.switchWindow(id).execute() }
        }
    }
}