package com.faircorp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.R
import com.faircorp.dto.HeaterDto
import com.faircorp.dto.HeaterStatus

class HeaterAdapter(val listener: OnHeaterSelectedListener) : RecyclerView.Adapter<HeaterAdapter.HeaterViewHolder>() { // (1)

    inner class HeaterViewHolder(view: View) : RecyclerView.ViewHolder(view) { // (2)
        val name: TextView = view.findViewById(R.id.txt_heater_name)
        val power: TextView = view.findViewById(R.id.txt_heater_power)
        val status: TextView = view.findViewById(R.id.txt_status_heater)
        val switch: SwitchCompat = view.findViewById(R.id.switch_heater)
    }
    private val items = mutableListOf<HeaterDto>() // (3)

    fun update(heaters: List<HeaterDto>) {  // (4)
        items.clear()
        items.addAll(heaters)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size // (5)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaterViewHolder { // (6)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_heaters_item, parent, false)
        return HeaterViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeaterViewHolder, position: Int) {  // (7)
        val heater = items[position]
        holder.apply {
            name.text = heater.name
            status.text = heater.heaterStatus.toString()
            power.text = heater.power.toString()
            switch.isChecked = heater.heaterStatus == HeaterStatus.ON
            itemView.setOnClickListener { listener.onHeaterSelected(heater.id) } // (1)
            switch.setOnCheckedChangeListener { _, _ ->
                listener.HeaterSwitchListener(heater.id)
            }
        }
    }
    override fun onViewRecycled(holder: HeaterAdapter.HeaterViewHolder) { // (2)
        super.onViewRecycled(holder)
        holder.apply {
            itemView.setOnClickListener(null)
        }

    }
}