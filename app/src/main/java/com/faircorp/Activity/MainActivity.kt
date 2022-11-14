package com.faircorp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.R
import com.faircorp.api.ApiServices
import com.faircorp.model.BuildingAdapter
import com.faircorp.model.OnBuildingSelectedListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BasicActivity(), OnBuildingSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.list_buildings) // (2)
        val adapter = BuildingAdapter(this) // (3)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices.BuildingsApiService.findAll().execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        adapter.update(it.body() ?: emptyList())
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Error on Buildings loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    companion object{
        const val WINDOW_ID= "com.faircorp.windowid.attribute"
        const val HEATER_ID= "com.faircorp.heaterid.attribute"
        const val HEATER_NAME_PARAM= "com.faircorp.heatername.attribute"
        const val WINDOW_NAME_PARAM = "com.faircorp.windowname.attribute"
        const val BUILDING_ID= "com.faircorp.buildingid.attribute"
        const val ROOM_ID= "com.faircorp.roomid.attribute"
    }

    override fun onBuildingSelected(id: Long) {
        val intent = Intent(this, RoomsActivity::class.java).putExtra(BUILDING_ID, id)
        startActivity(intent)
    }


}