package com.faircorp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.R
import com.faircorp.dto.RoomDto

interface RoomListener {
    fun onWindowClicked(id: Long)
    fun onHeaterClicked(id: Long)
    fun onRoomSelected(id: Long)
}
class RoomAdapter(private val listener: RoomListener) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    inner class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.room_name)
        val building: TextView = view.findViewById(R.id.building_number)
        val currentTemperature: TextView = view.findViewById(R.id.current_temperature)
        val targetTemperature: TextView = view.findViewById(R.id.target_temperature)
        val heaterBtn: Button = view.findViewById(R.id.btn_heater)
        val windowBtn: Button = view.findViewById(R.id.btn_window)
    }

    private val items = mutableListOf<RoomDto>()

    fun update(rooms: List<RoomDto>) {
        items.clear()
        items.addAll(rooms)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder { // (6)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_rooms_item, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {  // (7)
        val room = items[position]
        holder.apply {
            name.text = room.name
            building.text = room.buildingId.toString()
            currentTemperature.text = room.current_temperature.toString()
            targetTemperature.text = room.target_temperature.toString()
            itemView.setOnClickListener { listener.onRoomSelected(room.id) }
            heaterBtn.setOnClickListener {
                listener.onHeaterClicked(room.id)
            }

            windowBtn.setOnClickListener {
                listener.onWindowClicked(room.id)
            }
        }
    }
}